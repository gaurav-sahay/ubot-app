package com.sap.ubot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="customer_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetails implements Serializable{
	private static final long serialVersionUID = -8454477451420762128L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name ="account_number")
	private long accountNumber;
	
	@JsonProperty("name")
	@Column(name ="cust_name")
	private String custName;
	
	@Column(name ="phone_no")
	private String phoneNumber;
	private String email;
	
	
	

}
