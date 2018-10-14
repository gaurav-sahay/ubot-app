package com.sap.ubot.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sap.ubot.dto.Content;
import com.sap.ubot.dto.DateTimeEntity;
import com.sap.ubot.dto.DurationEntity;
import com.sap.ubot.dto.QuickReplies;
import com.sap.ubot.dto.QuickReplyButton;
import com.sap.ubot.dto.ResponseDTO;
import com.sap.ubot.dto.TextReply;
import com.sap.ubot.entity.MeterReadingDetails;
import com.sap.ubot.repository.BillingDetailsRepository;

@Service
public class BillDetailsService {
	
	@Autowired
	private BillingDetailsRepository billingDetailsRepository;
	
	
	public ResponseDTO getPaymentHistoryByDateTimeAndDevice(DateTimeEntity dateTimeEntity, String device, boolean isConsumption) {
		
		String currentDate = LocalDate.now().toString();
		String mrDate = dateTimeEntity.getIso();
		String rawDateTime = dateTimeEntity.getRaw();
		//QuickReplies quickReplies =  null;
		//String currentMonthContent = null;
		if(isCurrent(dateTimeEntity, currentDate)) {
			mrDate = currentDate;
			//quickReplies = prepareQuickRepliesForCurrentMonthBill();
			//currentMonthContent = "Do you want to pay now ?";
		}else if(StringUtils.isEmpty(mrDate)  && (rawDateTime.toLowerCase().contains("last") || rawDateTime.toLowerCase().contains("previous"))) {
			mrDate = LocalDate.now().minusMonths(1).toString();
		}else if(StringUtils.isEmpty(mrDate)  && rawDateTime.toLowerCase().contains("next")) {
			mrDate = LocalDate.now().plusMonths(1).toString();
		}
		mrDate = mrDate.substring(0, mrDate.lastIndexOf("-"));
		MeterReadingDetails meterReadingDetail = billingDetailsRepository.findByMrDate(mrDate,device);
		String content = null;
		if(isConsumption) {
			content = "Your Consumption details ";
		} else {
			content = "Your Bill details ";
		}
		List<Object> replies = new ArrayList<>();
		List<MeterReadingDetails> meterReadingDetails = new ArrayList<>();
		meterReadingDetails.add(meterReadingDetail);
		prepareResponseDTO(rawDateTime, meterReadingDetails, content, replies, isConsumption);
		/*if(quickReplies != null) {
			replies.add(quickReplies);
		}*/
		/*if(!StringUtils.isEmpty(currentMonthContent)) {
			TextReply textReply = (TextReply)replies.get(0);
			textReply.setContent(textReply.getContent().toString()+currentMonthContent);
		}*/
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setStatus(HttpStatus.OK.value()+"");
		responseDTO.setReplies(replies);
		return responseDTO;
		
	}
	
	
	//need to add validation if user input next 2 months or 3 months payments history. We should reply back saying No bills for future dates 
	
	public ResponseDTO getPaymentHistoryByDurationAndDevice(DurationEntity durationEntity, String device, boolean isConsumption) {
		String rawDuration = durationEntity.getRaw();
		List<MeterReadingDetails> meterReadingDetails = getMeterReadingDetailsList(durationEntity, device);
		String content = null;
		if(isConsumption) {
			content = "Your Consumption details ";
		} else {
			content = "Your Bill details ";
		}
		List<Object> replies = new ArrayList<>();
		prepareResponseDTO(rawDuration, meterReadingDetails, content, replies,isConsumption);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setStatus(HttpStatus.OK.value()+"");
		responseDTO.setReplies(replies);
		return responseDTO;
	}
	
	
	
	public ResponseDTO getOutstandingBalance(String device) {
		
		float totalSum = 0.0f;
		float totalPenalty = 0.0f;
		List<Object> replies = new ArrayList<>();
		TextReply reply = new TextReply();
		String content = null;
		reply.setType("text");
		
		List<MeterReadingDetails> meterReadingDetails = billingDetailsRepository.findOustandingBalance(device);
		if(meterReadingDetails != null && meterReadingDetails.size() != 0) {
			totalPenalty = 50 * meterReadingDetails.size();
			for(MeterReadingDetails meterReadingDetail: meterReadingDetails) {
				totalSum+=meterReadingDetail.getBillAmount();
			}
			
			content = "Total outstanding bill: \r Rs."+totalSum+".\r\n"+
					 "Late fee charges: \r Rs."+totalPenalty+".\r\n"+
					 "Amount payable : \r Rs."+(totalSum+totalPenalty)+".\r\n"+
					 "Do you want to pay now ?";
		}else {
			
			content = "No outstanding balance.";
		}
		
		reply.setContent(content);
		replies.add(reply);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setStatus(HttpStatus.OK.value()+"");
		responseDTO.setReplies(replies);
		return responseDTO;
		
		
	}
	

	private void prepareResponseDTO(String rawDuration, List<MeterReadingDetails> meterReadingDetails, String content, List<Object> replies, boolean isConsumption) {
		TextReply reply = new TextReply();
		reply.setType("text");
		if(meterReadingDetails != null && meterReadingDetails.size() != 0) {
			content += "for "+rawDuration+".\r\n";
			//reply.setContent();
			//replies.add(reply);
			for(MeterReadingDetails meterReadingDetail : meterReadingDetails) {
				//TextReply textReply = new TextReply();
				//textReply.setType("text");
				if(isConsumption) {
					String meterReadingDetailContent = "Meter Reading Date: \r"+meterReadingDetail.getMrDate()+".\r\n"+
							"Meter Read: \r"+meterReadingDetail.getMrRead()+" units.\r\n"+
							"Consumption: \r"+meterReadingDetail.getConsumption()+" units.\r\n\n";
					content += meterReadingDetailContent;
					//textReply.setContent(meterReadingDetailContent);
				}else {
					content += meterReadingDetail.toString();
					//reply.setContent(meterReadingDetail.toString());
				}
				//replies.add(textReply);
			}
			reply.setContent(content);
			replies.add(reply);
		}else {
			reply.setContent(content+"not available for "+rawDuration);
			replies.add(reply);
		}
		
		
	}
	
	private List<MeterReadingDetails> getMeterReadingDetailsList(DurationEntity durationEntity, String device){
		String rawDuration = durationEntity.getRaw();
		LocalDate localDate = LocalDate.now();
		String endDate = localDate.toString();
		String startDate = null;
		if(rawDuration.toLowerCase().contains("month")) {
			startDate = localDate.minusMonths((long) durationEntity.getMonths()).toString();
		}else if(rawDuration.toLowerCase().contains("year")) {
			startDate = localDate.minusYears((long) durationEntity.getYears()).toString();
		}
		return billingDetailsRepository.findByMrDateRange(startDate, endDate, device);
	}
	
	
	private boolean isCurrent(DateTimeEntity dateTimeEntity, String currentDate) {
		if(dateTimeEntity.getRaw() != null) {
			if(StringUtils.isEmpty(dateTimeEntity.getIso()) && dateTimeEntity.getRaw().toLowerCase().contains("current")) {
				return true;
			}else{
				String mrDate = dateTimeEntity.getIso();
				return currentDate.substring(0, currentDate.lastIndexOf("-")).equals(mrDate.substring(0, mrDate.lastIndexOf("-")));
			}
		}
		return false;
	}
	
	
	private QuickReplies prepareQuickRepliesForCurrentMonthBill() {
		QuickReplies quickReplies = new QuickReplies();
		quickReplies.setType("quickReplies");
		Content content = new Content();
		content.setTitle("Would you like to :");
		List<QuickReplyButton> quickReplyButtons = new ArrayList<>();
		QuickReplyButton payNow = new QuickReplyButton();
		payNow.setTitle("Pay Now");
		payNow.setValue("Pay Now");
		QuickReplyButton payLater = new QuickReplyButton();
		payLater.setTitle("Pay Later");
		payLater.setValue("Pay Later");
		quickReplyButtons.add(payNow);
		quickReplyButtons.add(payLater);
		content.setButtons(quickReplyButtons);
		quickReplies.setContent(content);
		return quickReplies;
	}
	
	
	

}
