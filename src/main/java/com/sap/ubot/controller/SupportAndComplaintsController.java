package com.sap.ubot.controller;

import java.text.ParseException;

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
import com.sap.ubot.service.SupportAndComplaintsService;

@RestController
@RequestMapping("/api/v1")
public class SupportAndComplaintsController {
	
	
	@Autowired
	private SupportAndComplaintsService supportAndComplaintsService;
	
	@RequestMapping(value = "/supportAndComplaints/onPremiseSupport",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<Object> onPremiseSupport(@RequestBody BillRequestDTO botRequestDTO){
		NumberEntity registeredId = botRequestDTO.getMemory().getRegisteredId();
		String scheduleTime = null;
		ResponseDTO responseDTO = null;
		DateTimeEntity dateTimeEntity = botRequestDTO.getMemory().getDateTimeEntity();
		DurationEntity durationEntity = botRequestDTO.getMemory().getDurationEntity();
		if(!StringUtils.isEmpty(dateTimeEntity.getRaw())) {
			scheduleTime = dateTimeEntity.getIso();
		}else {
			scheduleTime = durationEntity.getRaw();
		}
		try {
			responseDTO = supportAndComplaintsService.scheduleAppointment(registeredId,scheduleTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(responseDTO == null) {
			String content = "Sorry I didn't get you. \r\n"
					+ "Please re-enter Month and Year!";
			supportAndComplaintsService.fallBackResponse(content,responseDTO);
			return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(responseDTO,HttpStatus.OK);

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
