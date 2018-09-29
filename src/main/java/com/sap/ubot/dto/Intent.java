package com.sap.ubot.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Intent implements Serializable {
	private static final long serialVersionUID = -7515100131748402697L;
	
	private String slug;
	private String confidence;
	private String description;
	
	
	

}
