package com.sap.ubot.notification.sender;

import javax.mail.MessagingException;

import org.springframework.stereotype.Component;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Component
public class NotificationSender {
	
	//@Autowired
	//private JavaMailSenderImpl mailSender;
	
	
	public void sendNotification(String custPhoneNo, String message) throws MessagingException, UnirestException {
		/*JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		Properties properties = new Properties();

		properties.put("mail.smtp.host", "smtp-pulse.com");

		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.enable", "true");

		mailSender.setJavaMailProperties(properties);
		mailSender.setUsername("rohitkmar25@gmail.com");
		mailSender.setPassword(getPassword());
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("rohit.kumar16@sap.com");
		helper.setSubject("test mail");
		helper.setText("test mail", true); // true to activate multipart
		helper.addTo("gauarv.sahay@sap.com");
		mailSender.send(message);*/
		
		HttpResponse<String> response = Unirest.get("http://api.msg91.com/api/sendhttp.php?country=91&sender=MSGIND&route=4&mobiles=+91"+custPhoneNo+"&authkey=241907A7GpatcJf25bbc763b&message="+message)
				  .asString();
		
		System.out.println(response);
		
	}

	private String getPassword() {
		return "NFWQNQfNZ6S";
	}


}
