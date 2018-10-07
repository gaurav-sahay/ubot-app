package com.sap.ubot.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="customerInfo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfo implements Serializable {
	private static final long serialVersionUID = 3019230516615763113L;
	
	@EmbeddedId
	private CustomerInfoKey customerInfoKey;
	private long installation;
	private String rateCategory;
	private long materialCode;
	private Date moveInDate;
	private String mru;
	private int connectionState;

}
