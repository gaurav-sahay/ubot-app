package com.sap.ubot.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillRequestDTO implements Serializable {
	private static final long serialVersionUID = 4588984670635690230L;
	
	@JsonProperty("customerInfo")
	private Memory memory;

}
