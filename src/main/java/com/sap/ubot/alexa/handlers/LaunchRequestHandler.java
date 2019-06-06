package com.sap.ubot.alexa.handlers;

import java.util.Optional;

import org.json.JSONException;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.sap.ubot.recast.ai.RecastAiClient;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler /*implements RequestHandler*/ {/*

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(requestType(LaunchRequest.class));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		try {
			RecastAiClient.chatWithBot("CONVERSATION_START");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String speechText = "Welcome to the Alexa Skills Kit, you can say hello";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("HelloWorld", speechText)
                .withReprompt(speechText)
                .build();
	}

*/}
