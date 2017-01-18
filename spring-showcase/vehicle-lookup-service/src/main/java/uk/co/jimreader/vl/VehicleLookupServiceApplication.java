package uk.co.jimreader.vl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class VehicleLookupServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleLookupServiceApplication.class, args);
	}
}
