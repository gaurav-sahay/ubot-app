package com.sap.ubot.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuickReplies implements Serializable{
	private static final long serialVersionUID = -860762777185619985L;

	private String type;
	private Content content;
}
