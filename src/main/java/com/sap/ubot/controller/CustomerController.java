package com.sap.ubot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.ubot.dto.ResponseDTO;
import com.sap.ubot.entity.CustomerDetails;
import com.sap.ubot.service.CustomerDetailsManagementService;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {
	
	@Autowired
	private CustomerDetailsManagementService customerDetailsManagementService;
	

	@RequestMapping(value = "/customer/registerCustomer",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	
	public ResponseEntity<Object> registerCustomer(@RequestBody CustomerDetails customerDetails){
		ResponseDTO responseDTO = customerDetailsManagementService.registerCustomer(customerDetails);
		return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
	}
	

}
