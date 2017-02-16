package uk.co.jimreader.billing;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

interface BillingChannels {
	@Input
	SubscribableChannel billing();
}