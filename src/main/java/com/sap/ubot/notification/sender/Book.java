package com.sap.ubot.notification.sender;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Book {

	private String id;
	private String name;
	
	public Book(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public Book() {
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
