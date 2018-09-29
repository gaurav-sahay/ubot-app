package com.sap.ubot.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuickReplyButton implements Serializable{
	private static final long serialVersionUID = 4144562290470020958L;
	
	private String title;
	private String value;

}
