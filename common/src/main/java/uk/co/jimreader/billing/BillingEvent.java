package uk.co.jimreader.billing;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillingEvent {

	private String eventType;
	private String reference;
	private String user;
	private String date;
}
