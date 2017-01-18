package uk.co.jimreader.vl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@EnableBinding(BillingChannels.class)
@RestController
public class VehicleLookup {

	@Autowired
    private DiscoveryClient discoveryClient;

	private final MessageChannel channel;
	
	public VehicleLookup(BillingChannels channels) {
		this.channel = channels.billing();
	}
	
    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }
    
    @GetMapping("/vehicle/{vrn}")
    public Vehicle getVehicle(@PathVariable("vrn") String vrn) {
    	Message<String> msg = MessageBuilder.withPayload("Billing for VRN " + vrn).build();
    	this.channel.send(msg);
    	
    	return Vehicle.builder().make("Ford").model("Escort").registration(vrn).build();
    	
    }
}

interface BillingChannels {

	@Output
	MessageChannel billing();
}

