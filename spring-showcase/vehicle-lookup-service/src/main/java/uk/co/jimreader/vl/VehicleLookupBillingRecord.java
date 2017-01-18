package uk.co.jimreader.vl;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleLookupBillingRecord {

	private String vrn;
	private String user;
	private String date;
}
