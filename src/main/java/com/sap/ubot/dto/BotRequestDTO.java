package com.sap.ubot.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BotRequestDTO implements Serializable {
	private static final long serialVersionUID = -259165089653154070L;
	
	private NLP nlp;
	private String action_id;
	private Converstion conversation;

}
