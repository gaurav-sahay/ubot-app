package com.sap.ubot.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.sap.ubot.dto.NumberEntity;
import com.sap.ubot.dto.ResponseDTO;
import com.sap.ubot.dto.TextReply;
import com.sap.ubot.entity.CustomerInfo;
import com.sap.ubot.entity.TechnicianAssignmentPoolEntity;
import com.sap.ubot.entity.TechnicianEntity;
import com.sap.ubot.notification.sender.NotificationSender;
import com.sap.ubot.repository.CustomerInfoRepository;
import com.sap.ubot.repository.TechnicianAssignmentRepository;
import com.sap.ubot.repository.TechnicianRepository;
import com.sap.ubot.util.ConnectionStates;

@Service
public class SupportAndComplaintsService {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private TechnicianAssignmentRepository technicianAssignmentRepository;

	@Autowired
	private CustomerInfoRepository customerInfoRepository;

	@Autowired
	private TechnicianRepository technicianRepository;
	
	@Autowired
	private NotificationSender notificationSender;

	public ResponseDTO scheduleAppointment(NumberEntity registeredId, String scheduleTime) throws ParseException {
		Iterable<TechnicianEntity> technicians = technicianRepository.findAll();
		scheduleTime = scheduleTime.substring(0, scheduleTime.lastIndexOf("T"))+" "+scheduleTime.substring(scheduleTime.lastIndexOf("T")+1,scheduleTime.lastIndexOf("+"));
		ResponseDTO responseDTO = new ResponseDTO();
		List<Object> replies = new ArrayList<>();
		String content = null;
		CustomerInfo customerInfo = retrieveCustomerDevice(responseDTO, registeredId);
		if (customerInfo != null) {
			TechnicianEntity technicianEntity = getTechnicianFromPoolIfAvailable(technicians);
			if(technicianEntity == null) {
				for (TechnicianEntity technician : technicians) {
					if (checkTechnicianAvailability(technician, scheduleTime)) {
						long serviceReqId = assignTechnician(technician, customerInfo, scheduleTime);
						content = "Your service request (SR No:"+serviceReqId+") has been created.\r\n"+
								  "Your technician has been assigned for "+scheduleTime+".\r\n"+
								  "Your Technician details:\r\n"+
								  "Name: "+technician.getTechName()+".\r\n"+
								  "Contact No:"+technician.getTechPhoneNo()+".\r\n"+
								  "You will be receiving sms notification on your registered number.";
						
						prepareResponseDTO(replies,content);
						responseDTO.setStatus(HttpStatus.OK.value() + "");
						responseDTO.setReplies(replies);
						try {
							String smsContent = "(SR No:"+serviceReqId+")Your technician has been assigned for "+scheduleTime+". "+
									"Your Technician details: "+
									"Name: "+technician.getTechName()+". "+
									"Contact No:"+technician.getTechPhoneNo()+". ";
							smsContent = smsContent.replaceAll(" ", "%20");
							notificationSender.sendNotification(customerInfo.getCustomerInfoKey().getMobileNo(),smsContent);
						} catch (MessagingException | UnirestException e) {
							e.printStackTrace();
						}
						return responseDTO;
					}
				}
			} else {
				long serviceReqId = assignTechnician(technicianEntity, customerInfo, scheduleTime);
				content = "Your service request (SR No:"+serviceReqId+") has been created.\r\n"+
						"Your technician has been assigned for "+scheduleTime+". \r\n"+
						"Your Technician details: \r\n"+
						"Name: "+technicianEntity.getTechName()+" .\r\n"+
						"Contact No:"+technicianEntity.getTechPhoneNo()+" .\r\n"+
						"You will be receiving sms notification on your registered number.";
				prepareResponseDTO(replies,content);
				responseDTO.setStatus(HttpStatus.OK.value() + "");
				responseDTO.setReplies(replies);
				try {
					String smsContent = "(SR No:"+serviceReqId+")Your technician has been assigned for "+scheduleTime+". "+
							"Your Technician details: "+
							"Name: "+technicianEntity.getTechName()+". "+
							"Contact No:"+technicianEntity.getTechPhoneNo()+". ";
					smsContent = smsContent.replaceAll(" ", "%20");
					notificationSender.sendNotification(customerInfo.getCustomerInfoKey().getMobileNo(),smsContent);
				} catch (MessagingException | UnirestException e) {
					e.printStackTrace();
				}
				return responseDTO;
			}
		} else {
			return responseDTO;
		}
		content = "No Technicians are available for "+scheduleTime+". \r\n"+
						 "Do you want to schedule for some other time?";
		fallBackResponse(content, responseDTO);
		responseDTO.setStatus(HttpStatus.OK.value() + "");
		return responseDTO;
	}

	private TechnicianEntity getTechnicianFromPoolIfAvailable(Iterable<TechnicianEntity> technicians) {
		for (TechnicianEntity technician : technicians) {
			List<TechnicianAssignmentPoolEntity> technicianAssignmentPoolEntities = technicianAssignmentRepository
					.findByTechIdOrderByNextAvailableTimeDesc(technician.getTechId());
			if(technicianAssignmentPoolEntities == null || technicianAssignmentPoolEntities.size() == 0) {
				return technician;
			}
			
		}
		return null;
	}

	private void prepareResponseDTO(List<Object> replies, String content) {
		TextReply reply = new TextReply();
		reply.setType("text");
		reply.setContent(content);
		replies.add(reply);
	}

	private long assignTechnician(TechnicianEntity technician, CustomerInfo customerInfo, String scheduleTime)
			throws ParseException {
		TechnicianAssignmentPoolEntity technicianAssignmentPoolEntity = new TechnicianAssignmentPoolEntity();
		technicianAssignmentPoolEntity.setCustomerInfo(customerInfo.getCustomerInfoKey());
		Date scheduledTime = DATE_FORMAT.parse(scheduleTime);
		scheduledTime.setSeconds(0);
		technicianAssignmentPoolEntity.setScheduledTime(DATE_FORMAT.format(scheduledTime));
		Date nextAvailableDate = new Date(scheduledTime.getTime() + 1024 * 60 * 90);
		nextAvailableDate.setSeconds(0);
		technicianAssignmentPoolEntity.setNextAvailableTime(DATE_FORMAT.format(nextAvailableDate));
		technicianAssignmentPoolEntity.setTechId(technician.getTechId());
		technicianAssignmentPoolEntity = technicianAssignmentRepository.save(technicianAssignmentPoolEntity);
		return technicianAssignmentPoolEntity.getAssignmentId();

	}

	private boolean checkTechnicianAvailability(TechnicianEntity technician, String scheduleTime) throws ParseException {
		List<TechnicianAssignmentPoolEntity> technicianAssignmentPoolEntities = technicianAssignmentRepository
				.findByTechIdOrderByNextAvailableTimeDesc(technician.getTechId());
/*		if (technicianAssignmentPoolEntities.size() == 0) {
			return true;
		}
*/		TechnicianAssignmentPoolEntity assignmentPoolEntity = technicianAssignmentPoolEntities.get(0);
		Date scheduledTime = DATE_FORMAT.parse(scheduleTime);
		scheduledTime.setSeconds(0);
		Date nextAvailableDate = DATE_FORMAT.parse(assignmentPoolEntity.getNextAvailableTime());
		if (scheduledTime.equals(nextAvailableDate) || scheduledTime.after(nextAvailableDate)) {
			return true;
		}
		return false;
	}

	private CustomerInfo retrieveCustomerDevice(ResponseDTO fallBackResponseDTO, NumberEntity registeredId) {
		CustomerInfo customerInfo = null;
		if (registeredId != null && !StringUtils.isEmpty(registeredId.getRaw())) {
			try {
				customerInfo = customerInfoRepository.findByCustomerInfoKeyContractAccountOrCustomerInfoKeyMobileNo(
						Long.parseLong(registeredId.getRaw()), registeredId.getRaw());
			} catch (NumberFormatException numberFormatException) {
				String content = "Sorry the phone number or account number provided is not in correct format. \r\n";
				fallBackResponse(content, fallBackResponseDTO);
				return null;
			}
			if (customerInfo == null) {
				String content = "It seems you haven't initiated the request for the connection. \r\n"
						+ "Please go to Main Menu.\r\n" + "Then to connection related.\r\n" + "Then to move-in. ";
				fallBackResponse(content, fallBackResponseDTO);
				return null;
			}

		}
		if (customerInfo.getConnectionState() == ConnectionStates.DISCONNECTED.getState()
				|| customerInfo.getConnectionState() == ConnectionStates.TEMP_DISCONNECTED.getState()) {
			String content = "Your connection is disconnected. \r\n" + "Please initiate the re-connection request!";
			fallBackResponse(content, fallBackResponseDTO);
			return null;
		}
		return customerInfo;
	}

	public void fallBackResponse(String content, ResponseDTO fallBackResponseDTO) {
		TextReply reply = new TextReply();
		reply.setType("text");
		reply.setContent(content);
		fallBackResponseDTO.setStatus(HttpStatus.BAD_REQUEST.value() + "");
		List<Object> fallBackReplies = new ArrayList<>();
		fallBackReplies.add(reply);
		fallBackResponseDTO.setReplies(fallBackReplies);
	}

}
