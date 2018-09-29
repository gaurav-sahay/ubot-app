package com.sap.ubot.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class ResponseDTO implements Serializable {
	private static final long serialVersionUID = -6133337979445404189L;
	
	private String status;
	private List<Object> replies;
	

}
