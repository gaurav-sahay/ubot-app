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
import com.sap.ubot.dto.PhoneNumberEntity;
import com.sap.ubot.dto.ResponseDTO;
import com.sap.ubot.dto.TextReply;
import com.sap.ubot.entity.CustomerInfo;
import com.sap.ubot.repository.CustomerInfoRepository;
import com.sap.ubot.service.BillDetailsService;

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
		NumberEntity contractAccount = botRequestDTO.getMemory().getContractAccountNo();
		PhoneNumberEntity mobileNo = botRequestDTO.getMemory().getPhoneNo();
		ResponseDTO responseDTO = null;
		String device = retrieveCustomerDevice(responseDTO,contractAccount,mobileNo);
		
		if(StringUtils.isEmpty(device)) {
			return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
		}
		return null;
		
	}
	
	@RequestMapping(value = "/paymentAndBills/getBillingComplaints",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> getBillingComplaints(@RequestBody BillRequestDTO botRequestDTO){
		
		
		return null;
		
	}
	
	
	private ResponseEntity<Object> getPaymentOrConsumptionResponse(BillRequestDTO botRequestDTO, boolean isConsumption) {
		DateTimeEntity dateTimeEntity = botRequestDTO.getMemory().getDateTimeEntity();
		DurationEntity durationEntity = botRequestDTO.getMemory().getDurationEntity();
		
		NumberEntity contractAccount = botRequestDTO.getMemory().getContractAccountNo();
		PhoneNumberEntity mobileNo = botRequestDTO.getMemory().getPhoneNo();
		String rawDateTime = dateTimeEntity.getRaw();
		String rawDuration = durationEntity.getRaw();
		ResponseDTO responseDTO = null;
		String device = retrieveCustomerDevice(responseDTO,contractAccount,mobileNo);
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
		String content = "Sorry I didn't get you :( \r\n"
				+ "Please re-enter Month and Year!";
		ResponseDTO fallBackResponseDTO = fallBackResponse(content);
		return new ResponseEntity<>(fallBackResponseDTO,HttpStatus.BAD_REQUEST);
	}
	
	private String retrieveCustomerDevice(ResponseDTO fallBackResponseDTO, NumberEntity contractAccount, PhoneNumberEntity mobileNo) {
		CustomerInfo customerInfo= null;
		if(contractAccount != null && !StringUtils.isEmpty(contractAccount.getRaw())) {
			try {
				customerInfo = customerInfoRepository.findByCustomerInfoKeyContractAccount(Long.parseLong(contractAccount.getRaw()));
			} catch (NumberFormatException numberFormatException) {
				String content = "Sorry the phone number provided is not in correct format :( \r\n"
						+ "Please re-enter your phone number e.g +91-1234567890";
				fallBackResponseDTO = fallBackResponse(content);
				return null;
			}
			if(customerInfo == null) {
				String content = "It seems you haven't initiated the request for the connection :( \r\n"
						+ "Please go to : payments and bills -> connection related -> move-in ";
				fallBackResponseDTO = fallBackResponse(content);
				return null;
			}
			
		}else if(mobileNo != null && !StringUtils.isEmpty(mobileNo.getRaw())) {
			customerInfo = customerInfoRepository.findByCustomerInfoKeyMobileNo(mobileNo.getRaw());
			if(customerInfo == null) {
				String content = "It seems you haven't initiated the request for the connection :( \r\n"
						+ "Please go to : Main Menu -> connection related -> move-in ";
				fallBackResponseDTO = fallBackResponse(content);
				return null;
			}
			
		}
		return customerInfo.getCustomerInfoKey().getDevice()+"";
	}

	private ResponseDTO fallBackResponse(String content) {
		TextReply reply = new TextReply();
		reply.setType("text");
		reply.setContent(content);
		ResponseDTO fallBackResponseDTO = new ResponseDTO();
		fallBackResponseDTO.setStatus(HttpStatus.OK.value()+"");
		List<Object> fallBackReplies = new ArrayList<>();
		fallBackReplies.add(reply);
		fallBackResponseDTO.setReplies(fallBackReplies);
		return fallBackResponseDTO;
	}
	

}
