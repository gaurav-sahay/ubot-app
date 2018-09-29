package com.sap.ubot.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumberEntity implements Serializable {
	private static final long serialVersionUID = -2907610965348164750L;
	
	private String number;
	private String raw;
	private String confidence;
	
	

}
