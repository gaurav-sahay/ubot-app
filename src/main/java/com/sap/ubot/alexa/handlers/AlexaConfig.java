package com.sap.ubot.alexa.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazon.speech.speechlet.servlet.SpeechletServlet;

@Configuration
public class AlexaConfig {

	
	@Bean
	@Autowired
	public ServletRegistrationBean<SpeechletServlet> registerSkillServlet(HandlerSpeechlet handlerSpeechlet){
		SpeechletServlet speechletServlet = new SpeechletServlet();
		speechletServlet.setSpeechlet(handlerSpeechlet);
		return new ServletRegistrationBean<>(speechletServlet, "/api/v1/handleAlexaRequest");
	}
}
