package com.sap.ubot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sap.ubot.dto.ResponseDTO;
import com.sap.ubot.dto.TextReply;
import com.sap.ubot.entity.CustomerDetails;
import com.sap.ubot.repository.CustomerDetailsRepository;

@Service
public class CustomerDetailsManagementService {
	
	@Autowired
	private CustomerDetailsRepository customerDetailsRepository;
	
	
	public ResponseDTO registerCustomer(CustomerDetails customerDetails) {
		
		TextReply textReply = new TextReply();
		List<Object> replies = new ArrayList<>();
		CustomerDetails persistedCustDetails = customerDetailsRepository.save(customerDetails);
		textReply.setType("text");
		if(persistedCustDetails != null) {
			textReply.setContent("Generated account number is "+persistedCustDetails.getAccountNumber());
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
