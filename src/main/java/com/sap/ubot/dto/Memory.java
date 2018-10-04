package com.sap.ubot.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Memory implements Serializable {
	private static final long serialVersionUID = -2108202969686748738L;
	
	
	@JsonProperty("contract-account-no")
	private NumberEntity contractAccountNo;
	
	@JsonProperty("phone-no")
	private PhoneNumberEntity phoneNo;
	
	@JsonProperty("datetime")
	private DateTimeEntity dateTimeEntity;
	
	@JsonProperty("duration")
	private DurationEntity durationEntity;
	
	private String customerIdentifier;
	
}
