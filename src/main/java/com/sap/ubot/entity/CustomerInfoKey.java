package com.sap.ubot.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class CustomerInfoKey implements Serializable {
	private static final long serialVersionUID = 7298693629615793278L;
	
	private long businessPartner;
	private long contractAccount;
	private int device;
	private String mobileNo;
	
	

}
