package com.sap.ubot.dto;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class TextReply implements Serializable{
	private static final long serialVersionUID = -4757310407315636112L;
	
	private String type;
	private Object content;

}
