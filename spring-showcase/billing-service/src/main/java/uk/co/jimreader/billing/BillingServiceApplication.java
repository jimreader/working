package uk.co.jimreader.billing;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@EnableBinding(BillingChannels.class)
@EnableDiscoveryClient
@SpringBootApplication
public class BillingServiceApplication {

	@Bean
	@Scope("prototype")
	Logger logger (InjectionPoint ip) {
		return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
	}
	
	@Bean
	IntegrationFlow flow(BillingChannels channel, Logger logger) {
		return IntegrationFlows.from(channel.billing()).handle(String.class, (payload, headers) -> {
			logger.info(payload);
			return null;
		}).get();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}
}
