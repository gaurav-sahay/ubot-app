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
		DateTimeEntity dateTimeEntity = botRequestDTO.getMemory().getDateTimeEntity();
		DurationEntity durationEntity = botRequestDTO.getMemory().getDurationEntity();
		
		NumberEntity contractAccount = botRequestDTO.getMemory().getContractAccountNo();
		PhoneNumberEntity mobileNo = botRequestDTO.getMemory().getPhoneNo();
		/*String customerIdentifier = botRequestDTO.getMemory().getCustomerIdentifier();
		if(customerIdentifier.equals("cust-phone") && StringUtils.isEmpty(mobileNo.getRaw())) {
			TextReply reply = new TextReply();
			reply.setType("text");
			reply.setContent("Sorry the phone number provided is not in correct format :( \r\n"
					+ "Please re-enter your phone number e.g +91-1234567890");
			ResponseDTO fallBackResponseDTO = fallBackResponse(reply);
			return new ResponseEntity<>(fallBackResponseDTO,HttpStatus.BAD_REQUEST);
		}else if(customerIdentifier.equals("cust-account-number") && StringUtils.isEmpty(contractAccount.getRaw())) {
			TextReply reply = new TextReply();
			reply.setType("text");
			reply.setContent("Sorry the account number provided is not correct :( \r\n"
					+ "Please re-enter your account number");
			ResponseDTO fallBackResponseDTO = fallBackResponse(reply);
			return new ResponseEntity<>(fallBackResponseDTO,HttpStatus.BAD_REQUEST);
		}*/
		String device = null;
		if(contractAccount != null && !StringUtils.isEmpty(contractAccount.getRaw())) {
			CustomerInfo customerInfo= null;
			try {
				customerInfo = customerInfoRepository.findByCustomerInfoKeyContractAccount(Long.parseLong(contractAccount.getRaw()));
			} catch (NumberFormatException numberFormatException) {
				TextReply reply = new TextReply();
				reply.setType("text");
				reply.setContent("Sorry the phone number provided is not in correct format :( \r\n"
						+ "Please re-enter your phone number e.g +91-1234567890");
				ResponseDTO fallBackResponseDTO = fallBackResponse(reply);
				return new ResponseEntity<>(fallBackResponseDTO,HttpStatus.BAD_REQUEST);
			}
			if(customerInfo == null) {
				TextReply reply = new TextReply();
				reply.setType("text");
				reply.setContent("It seems you haven't initiated the request for the connection :( \r\n"
						+ "Please go to : payments and bills -> connection related -> move-in ");
				ResponseDTO fallBackResponseDTO = fallBackResponse(reply);
				return new ResponseEntity<>(fallBackResponseDTO,HttpStatus.NOT_FOUND);
			}
			device = customerInfo.getCustomerInfoKey().getDevice()+"";
			
		}else if(mobileNo != null && !StringUtils.isEmpty(mobileNo.getRaw())) {
			CustomerInfo customerInfo = customerInfoRepository.findByCustomerInfoKeyMobileNo(mobileNo.getRaw());
			if(customerInfo == null) {
				TextReply reply = new TextReply();
				reply.setType("text");
				reply.setContent("It seems you haven't initiated the request for the connection :( \r\n"
						+ "Please go to : payments and bills -> connection related -> move-in ");
				ResponseDTO fallBackResponseDTO = fallBackResponse(reply);
				return new ResponseEntity<>(fallBackResponseDTO,HttpStatus.NOT_FOUND);
			}
			device = customerInfo.getCustomerInfoKey().getDevice()+"";
		}
		
		String rawDateTime = dateTimeEntity.getRaw();
		String rawDuration = durationEntity.getRaw();
		if(!StringUtils.isEmpty(rawDuration)) {
			ResponseDTO responseDTO = billDetailsService.getPaymentHistoryByDurationAndDevice(durationEntity, device);
			return new ResponseEntity<>(responseDTO,HttpStatus.OK);
			
		} else if(!StringUtils.isEmpty(rawDateTime)) {
			ResponseDTO responseDTO = billDetailsService.getPaymentHistoryByDateTimeAndDevice(dateTimeEntity, device);
			return new ResponseEntity<>(responseDTO,HttpStatus.OK);

		} else {
			TextReply reply = new TextReply();
			reply.setType("text");
			reply.setContent("Sorry I didn't get you :( \r\n"
					+ "Please re-enter Month and Year!");
			ResponseDTO fallBackResponseDTO = fallBackResponse(reply);
			return new ResponseEntity<>(fallBackResponseDTO,HttpStatus.BAD_REQUEST);
		}


	}
	
	@RequestMapping(value = "/paymentAndBills/getConsumptionDetails",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> getConsumptionDetails(@RequestBody BillRequestDTO botRequestDTO){
		return null;
		
	}
	
	@RequestMapping(value = "/paymentAndBills/getOutstandingBill",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> getOutstandingBill(@RequestBody BillRequestDTO botRequestDTO){
		return null;
		
	}
	
	@RequestMapping(value = "/paymentAndBills/getBillingComplaints",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> getBillingComplaints(@RequestBody BillRequestDTO botRequestDTO){
		return null;
		
	}
	

	private ResponseDTO fallBackResponse(TextReply reply) {
		ResponseDTO fallBackResponseDTO = new ResponseDTO();
		fallBackResponseDTO.setStatus(HttpStatus.OK.value()+"");
		List<Object> fallBackReplies = new ArrayList<>();
		fallBackReplies.add(reply);
		fallBackResponseDTO.setReplies(fallBackReplies);
		return fallBackResponseDTO;
	}
	

}
