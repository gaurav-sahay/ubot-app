package com.sap.ubot.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DurationEntity implements Serializable {
	private static final long serialVersionUID = 6916159755789797532L;
	
	private String chrono;
	private double years;
	private double months;
	private float days;
	private float hours;
	private float minutes;
	private double seconds;
	private String raw;
	private String confidence;
	

}
