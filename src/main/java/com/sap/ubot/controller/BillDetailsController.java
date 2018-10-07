package com.sap.ubot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.ubot.dto.BillRequestDTO;
import com.sap.ubot.dto.DateTimeEntity;
import com.sap.ubot.dto.DurationEntity;
import com.sap.ubot.dto.NumberEntity;
import com.sap.ubot.dto.ResponseDTO;
import com.sap.ubot.dto.TextReply;
import com.sap.ubot.entity.CustomerInfo;
import com.sap.ubot.repository.CustomerInfoRepository;
import com.sap.ubot.service.BillDetailsService;
import com.sap.ubot.util.ConnectionStates;

@RestController
@RequestMapping("/api/v1")
public class BillDetailsController {

	@Autowired
	private BillDetailsService billDetailsService;
	
	@Autowired
	private CustomerInfoRepository customerInfoRepository; 


	@RequestMapping(value = "/paymentAndBills/getPaymentHistory",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> getPaymentHistory(@RequestBody BillRequestDTO botRequestDTO){
		return getPaymentOrConsumptionResponse(botRequestDTO,false);


	}

	
	
	@RequestMapping(value = "/paymentAndBills/getConsumptionDetails",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> getConsumptionDetails(@RequestBody BillRequestDTO botRequestDTO){
		return getPaymentOrConsumptionResponse(botRequestDTO,true);
		
		
	}
	
	
	
	@RequestMapping(value = "/paymentAndBills/getOutstandingBill",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> getOutstandingBill(@RequestBody BillRequestDTO botRequestDTO){
		NumberEntity registeredId = botRequestDTO.getMemory().getRegisteredId();
		ResponseDTO responseDTO = new ResponseDTO();
		String device = retrieveCustomerDevice(responseDTO,registeredId);
		
		if(StringUtils.isEmpty(device)) {
			return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
		}
		responseDTO  = billDetailsService.getOutstandingBalance(device);
		if(responseDTO != null) {
			return new ResponseEntity<>(responseDTO,HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST); 
		
	}
	
	@RequestMapping(value = "/paymentAndBills/getBillingComplaints",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> getBillingComplaints(@RequestBody BillRequestDTO botRequestDTO){
		
		
		return null;
		
	}
	
	
	private ResponseEntity<Object> getPaymentOrConsumptionResponse(BillRequestDTO botRequestDTO, boolean isConsumption) {
		DateTimeEntity dateTimeEntity = botRequestDTO.getMemory().getDateTimeEntity();
		DurationEntity durationEntity = botRequestDTO.getMemory().getDurationEntity();
		
		NumberEntity registeredId = botRequestDTO.getMemory().getRegisteredId();
		String rawDateTime = dateTimeEntity.getRaw();
		String rawDuration = durationEntity.getRaw();
		ResponseDTO responseDTO = new ResponseDTO();
		String device = retrieveCustomerDevice(responseDTO,registeredId);
		if(StringUtils.isEmpty(device)) {
			return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
		}
		if(!StringUtils.isEmpty(rawDuration)) {
			responseDTO = billDetailsService.getPaymentHistoryByDurationAndDevice(durationEntity, device, isConsumption);
			return new ResponseEntity<>(responseDTO,HttpStatus.OK);
			
		}
		if(!StringUtils.isEmpty(rawDateTime)) {
			responseDTO = billDetailsService.getPaymentHistoryByDateTimeAndDevice(dateTimeEntity, device, isConsumption);
			return new ResponseEntity<>(responseDTO,HttpStatus.OK);

		} 
		String content = "Sorry I didn't get you. \r\n"
				+ "Please re-enter Month and Year!";
		fallBackResponse(content,responseDTO);
		return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
	}
	
	private String retrieveCustomerDevice(ResponseDTO fallBackResponseDTO, NumberEntity registeredId) {
		CustomerInfo customerInfo= null;
		if(registeredId != null && !StringUtils.isEmpty(registeredId.getRaw())) {
			try {
				customerInfo = customerInfoRepository.findByCustomerInfoKeyContractAccountOrCustomerInfoKeyMobileNo(Long.parseLong(registeredId.getRaw()), 
						registeredId.getRaw());
			} catch (NumberFormatException numberFormatException) {
				String content = "Sorry the phone number or account number provided is not in correct format. \r\n";
				fallBackResponse(content,fallBackResponseDTO);
				return null;
			}
			if(customerInfo == null) {
				String content = "It seems you haven't initiated the request for the connection. \r\n"
						+ "Please go to Main Menu.\r\n"
						+ "Then to connection related.\r\n"
						+ "Then to move-in. ";
				fallBackResponse(content, fallBackResponseDTO);
				return null;
			}
			
		}
		if(customerInfo.getConnectionState() == ConnectionStates.DISCONNECTED.getState() || 
				customerInfo.getConnectionState() == ConnectionStates.TEMP_DISCONNECTED.getState()) {
			String content = "Your connection is disconnected. \r\n"+
							 "Please initiate the re-connection request!";
			fallBackResponse(content,fallBackResponseDTO);
			return null;
		}
		return customerInfo.getCustomerInfoKey().getDevice()+"";
	}

	private ResponseDTO fallBackResponse(String content, ResponseDTO fallBackResponseDTO) {
		TextReply reply = new TextReply();
		reply.setType("text");
		reply.setContent(content);
		//ResponseDTO fallBackResponseDTO = new ResponseDTO();
		fallBackResponseDTO.setStatus(HttpStatus.BAD_REQUEST.value()+"");
		List<Object> fallBackReplies = new ArrayList<>();
		fallBackReplies.add(reply);
		fallBackResponseDTO.setReplies(fallBackReplies);
		return fallBackResponseDTO;
	}
	

}
