package com.sap.ubot.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Content implements Serializable {
	private static final long serialVersionUID = -5996486873401270397L;
	
	private String title;
	private List<QuickReplyButton> buttons;

}
