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
public class ConnectionRelatedController {
	
	
	@RequestMapping(value = "/connectionRelated/moveIn",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> moveIn(@RequestBody BillRequestDTO botRequestDTO){
		
		return null;

	}
	
	@RequestMapping(value = "/connectionRelated/moveOut",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> moveOutOrDisconnection(@RequestBody BillRequestDTO botRequestDTO){
		return null;

	} 
	
	@RequestMapping(value = "/connectionRelated/reConnection",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> reConnection(@RequestBody BillRequestDTO botRequestDTO){
		return null;

	}
	
	
	

}
