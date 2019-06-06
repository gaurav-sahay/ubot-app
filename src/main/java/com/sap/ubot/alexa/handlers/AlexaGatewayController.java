package com.sap.ubot.alexa.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.ask.model.RequestEnvelope;

//@RestController
//@RequestMapping("/api/v1")
public class AlexaGatewayController{
	
	
	
	/*@PostMapping(value = "/handleAlexaRequest",
			produces = { MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> handleAlexaRequest(@RequestBody RequestEnvelope alexaRequest){
		
		
		
		return new ResponseEntity<>("{\r\n" + 
				"	\"body\": {\r\n" + 
				"		\"version\": \"1.0\",\r\n" + 
				"		\"response\": {\r\n" + 
				"			\"outputSpeech\": {\r\n" + 
				"				\"type\": \"SSML\",\r\n" + 
				"				\"ssml\": \"<speak>Namaste.\\nWelcome to u bot.\\nHow can i help you today?\\nIn case of new connection or registration, please say \\\"Please register me\\\".</speak>\"\r\n" + 
				"			},\r\n" + 
				"			\"shouldEndSession\": false\r\n" + 
				"		},\r\n" + 
				"		\"sessionAttributes\": {},\r\n" + 
				"		\"userAgent\": \"ask-node/2.0.7 Node/v8.12.0\"\r\n" + 
				"	}\r\n" + 
				"}",HttpStatus.OK);
	}*/
	
	

}
