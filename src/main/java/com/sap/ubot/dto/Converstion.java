package com.sap.ubot.dto;

import java.io.Serializable;

import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Converstion implements Serializable {
	private static final long serialVersionUID = 3333729130440867815L;
	
	private String id;
	private String conversation_id;
	private String warning;
	private String language;
	private Memory memory;
	private String [] skill_stack;
	private String skill;
	private String skill_occurences;
	private JSONObject participant_data;
	private String test;

}
