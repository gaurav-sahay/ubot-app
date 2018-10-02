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
		String device = null;
		if(contractAccount != null && !StringUtils.isEmpty(contractAccount.getRaw())) {
			device = customerInfoRepository.findByCustomerInfoKeyContractAccount(Long.parseLong(contractAccount.getRaw())).getCustomerInfoKey().getDevice()+"";
		}else if(mobileNo != null && !StringUtils.isEmpty(mobileNo.getRaw())) {
			device = customerInfoRepository.findByCustomerInfoKeyMobileNo(mobileNo.getRaw()).getCustomerInfoKey().getDevice()+"";
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
			List<Object> replies = new ArrayList<>();
			TextReply reply = new TextReply();
			ResponseDTO fallBackResponseDTO = fallBackResponseForBillAndPayments(replies, reply);
			return new ResponseEntity<>(fallBackResponseDTO,HttpStatus.BAD_REQUEST);
		}


	}
	
	@RequestMapping(value = "/paymentAndBills/getOutstandingBill",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> getOutstandingBill(@RequestBody BillRequestDTO botRequestDTO){
		return null;
		
	}
	

	private ResponseDTO fallBackResponseForBillAndPayments(List<Object> replies, TextReply reply) {
		ResponseDTO fallBackResponseDTO = new ResponseDTO();
		fallBackResponseDTO.setStatus(HttpStatus.OK.value()+"");
		TextReply fallBackTextReply = new TextReply();
		List<Object> fallBackReplies = new ArrayList<>();
		fallBackTextReply.setType("text");
		fallBackTextReply.setContent("Sorry I didn't get you :( \r\n"
				+ "Please re-enter Month and Year!");
		fallBackReplies.add(reply);
		fallBackResponseDTO.setReplies(replies);
		return fallBackResponseDTO;
	}



	



	




}
