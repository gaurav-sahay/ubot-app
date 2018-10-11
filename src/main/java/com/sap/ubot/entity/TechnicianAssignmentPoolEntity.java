package com.sap.ubot.entity;

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
@Table(name="technician_assignment_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicianAssignmentPoolEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long assignmentId;
	
	private long techId;
	private CustomerInfoKey customerInfo;
	private String scheduledTime;
	private String nextAvailableTime;
	

}
