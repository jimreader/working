package uk.co.jimreader.vl;

import java.util.Calendar;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Keeper {

	private Calendar startDate;
	
	private Calendar endDate;
}

