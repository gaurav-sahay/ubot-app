package com.sap.ubot.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Memory implements Serializable {
	private static final long serialVersionUID = -2108202969686748738L;
	
	@JsonProperty("business-partner-no")
	private NumberEntity businessPartnerNo;
	
	@JsonProperty("contract-account-no")
	private NumberEntity contractAccountNo;
	
	@JsonProperty("phone-no")
	private PhoneNumberEntity phoneNo;
	
	@JsonProperty("device")
	private NumberEntity device;
	
	@JsonProperty("datetime")
	private DateTimeEntity dateTimeEntity;
	
	@JsonProperty("duration")
	private DurationEntity durationEntity;
	
}
