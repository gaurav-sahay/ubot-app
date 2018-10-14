package com.sap.ubot.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.sap.ubot.dto.ResponseDTO;
import com.sap.ubot.dto.TextReply;
import com.sap.ubot.entity.CustomerDetails;
import com.sap.ubot.notification.sender.NotificationSender;
import com.sap.ubot.repository.CustomerDetailsRepository;

@Service
public class CustomerDetailsManagementService {
	
	@Autowired
	private CustomerDetailsRepository customerDetailsRepository;
	
	@Autowired
	private NotificationSender notificationSender;
	
	public ResponseDTO registerCustomer(CustomerDetails customerDetails) {
		
		TextReply textReply = new TextReply();
		List<Object> replies = new ArrayList<>();
		CustomerDetails persistedCustDetails = customerDetailsRepository.save(customerDetails);
		textReply.setType("text");
		if(persistedCustDetails != null) {
			textReply.setContent("Welcome aboard "+customerDetails.getCustName()+"! \r\nGenerated account number is "+persistedCustDetails.getAccountNumber()+
					". \r\n It will take 2 to 3 business days to process the request.\r\n SMS notification has beed send to you registered mobile number.\r\n Thank you.");
			String smsContent = "Welcome aboard "+customerDetails.getCustName()+"!Generated account number is "+persistedCustDetails.getAccountNumber()+
					".It will take 2 to 3 business days to process the request";
			smsContent = smsContent.replaceAll(" ", "%20");
			try {
				notificationSender.sendNotification(persistedCustDetails.getPhoneNumber(), smsContent);
			} catch (MessagingException | UnirestException e) {
				e.printStackTrace();
			}
		}else { 
			textReply.setContent("Sorry your account number can't be generated !");
		}

		replies.add(textReply);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setStatus(HttpStatus.OK.value()+"");
		responseDTO.setReplies(replies);
		return responseDTO;
		
	}
	
	
	

}
