package com.sap.ubot.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NumberEntity implements Serializable {
	private static final long serialVersionUID = -6254612354814382039L;
	
	private String scalar;
	private String raw;
	private String confidence;
	

}
