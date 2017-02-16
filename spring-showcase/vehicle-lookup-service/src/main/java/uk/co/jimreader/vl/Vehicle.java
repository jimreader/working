package uk.co.jimreader.vl;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String registration;
	private String vin;
	private String make;
	private String model;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd MMMM yyyy")
	private Calendar dateOfFirstRegistration;

}
