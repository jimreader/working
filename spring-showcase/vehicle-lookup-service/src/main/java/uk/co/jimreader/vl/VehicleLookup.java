package uk.co.jimreader.vl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.jimreader.billing.BillingEvent;

@EnableBinding(BillingChannels.class)
@RestController
public class VehicleLookup {

	private static final Logger log = LoggerFactory.getLogger(VehicleLookup.class);

	@Value("${dataservice.url}")
	private String url;
	
	@Value("${dataservice.apikey}")
	private String apiKey;

	private final MessageChannel channel;

	private final ObjectMapper mapper;
	
	private final VehicleRepository repo;

	public VehicleLookup(BillingChannels channels, ObjectMapper mapper, VehicleRepository repo) {
		this.mapper = mapper;
		this.repo = repo;
		this.channel = channels.billing();
	}

	@GetMapping("/vehicle/{vrn}")
	public Vehicle getVehicle(@PathVariable("vrn") String vrn) throws JsonProcessingException {
		Message<String> msg = MessageBuilder
				.withPayload(mapper.writeValueAsString(
						BillingEvent.builder().date(new Date().toString()).reference(vrn).user("test").build()))
				.build();
		this.channel.send(msg);

		Vehicle v =  lookupFromProvider(vrn);
		
		Vehicle v2 = repo.findByVin(v.getVin());
		
		if (v2 == null) {
			repo.save(v);
			return v;
		} else {
			if (isSameVehicle(v, v2)) {
					return v2;
			} else {
				v2.setRegistration(null);
				repo.save(v);
				return v;
			}
		}

	}
	
	private boolean isSameVehicle(Vehicle v, Vehicle v2) {
		return StringUtils.equals(v.getMake(), v2.getMake()) && StringUtils.equals(v.getModel(), v2.getModel()) && StringUtils.equals(v.getRegistration(), v2.getRegistration()) && StringUtils.equals(v.getVin(), v2.getVin());
	}

	private Vehicle lookupFromProvider(String vrn) {
		RestTemplate restTemplate = new RestTemplate();
		Vehicle quote = restTemplate.getForObject(url + "apikey=" + apiKey + "&licencePlate=" + vrn, Vehicle.class);
		quote.setRegistration(StringUtils.deleteWhitespace(vrn.toUpperCase()));
        log.info(quote.toString());
        return quote;
	}
}

interface BillingChannels {

	@Output
	MessageChannel billing();
}
