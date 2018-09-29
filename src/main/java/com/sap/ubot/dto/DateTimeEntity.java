package com.sap.ubot.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateTimeEntity implements Serializable {
	private static final long serialVersionUID = -1549366206344907845L;
	
	private String formatted;
	private String iso;
	private String accuracy;
	private String chronology;
	private String state;
	private String raw;
	private String confidence;

}
