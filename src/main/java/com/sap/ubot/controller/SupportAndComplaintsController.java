package com.sap.ubot.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.ubot.dto.BillRequestDTO;

@RestController
@RequestMapping("/api/v1")
public class SupportAndComplaintsController {
	
	@RequestMapping(value = "/supportAndComplaints/onPremiseSupport",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> onPremiseSupport(@RequestBody BillRequestDTO botRequestDTO){
		return null;

	}
	
	@RequestMapping(value = "/supportAndComplaints/serviceOutageInfo",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> serviceOutageInfo(@RequestBody BillRequestDTO botRequestDTO){
		return null;

	}
	
	@RequestMapping(value = "/supportAndComplaints/complaints",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> complaints(@RequestBody BillRequestDTO botRequestDTO){
		return null;

	}

}
