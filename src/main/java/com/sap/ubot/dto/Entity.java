package com.sap.ubot.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Entity implements Serializable {
	private static final long serialVersionUID = -4567524959560282136L;
	
	private List<DateTimeEntity> datetime;
	

}
