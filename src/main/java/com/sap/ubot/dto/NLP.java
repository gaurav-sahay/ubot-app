package com.sap.ubot.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NLP implements Serializable {
	private static final long serialVersionUID = 70122465997096762L;
	
	private String uuid;
	private String source;
	private List<Intent> intents;
	private String act;
	private String type;
	private String sentiment;
	private Entity entities;
	private String language;
	private String processing_language;
	private String version;
	private String timestamp;
	private String status;
	

}
