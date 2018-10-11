package com.sap.ubot.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="technician_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicianEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long techId;
	private String techName;
	private String techPhoneNo;
	private Date createdOn;
	

}
