package uk.co.jimreader.vl;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vehicle {

	private String registration;
	private String vin;
	private String make;
	private String model;

	private List<Keeper> keepers;
}
