package com.sap.ubot.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="meter_reading_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MeterReadingDetails implements Serializable {
	private static final long serialVersionUID = 137858179353222863L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private int device;
	private Date mrDate;
	private long mrRead;
	private long consumption;
	private float billAmount;
	private Date dueDate;
	private float paidAmount;
	@Column(nullable = true)
	private Date paymentDate;
	
	
	
	@Override
	public String toString() {
		String paidDate = null;
		if(paymentDate == null) {
			paidDate = "Not Paid";
		}else {
			paidDate = "Paid on "+paymentDate.toString();
		}
		return 	"Meter Reading Date: \r"+mrDate+".\r\n"+
				/*"Meter Read:\r\n"+mrRead+"\r\n"+*/
				"Consumption: \r"+consumption+" units.\r\n"+
				"Bill Amount: \r Rs."+billAmount+".\r\n"+
				"Due Date: \r"+dueDate+".\r\n"+
				/*"Paid Amount:\r\n"+paidAmount+"\r\n"+*/
				"Payment Status : \r"+paidDate+".\r\n\n";
	}
	
	

}
