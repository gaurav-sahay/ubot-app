package com.sap.ubot.alexa.handlers;

import org.json.JSONException;
import org.springframework.stereotype.Component;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.sap.ubot.recast.ai.RecastAiClient;

@Component
public class HandlerSpeechlet implements SpeechletV2 {
	

	@Override
	public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		Session session = requestEnvelope.getSession();
		AlexaUtils.setConversationMode(session, true);
		String speechText = "welcome to my intent";
		Card card = AlexaUtils.newCard("Trivia", speechText.toString());
		PlainTextOutputSpeech speech = AlexaUtils.newSpeech(speechText.toString(), AlexaUtils.inConversationMode(session));

		return AlexaUtils.newSpeechletResponse( card, speech, session, false);
	}

	@Override
	public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
		Session session = requestEnvelope.getSession();
		AlexaUtils.setConversationMode(session, true);
		String speechText = null;
		try {
			speechText = RecastAiClient.chatWithBot("CONVERSATION_START");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Card card = AlexaUtils.newCard("Trivia", speechText.toString());
		PlainTextOutputSpeech speech = AlexaUtils.newSpeech(speechText.toString(), AlexaUtils.inConversationMode(session));

		return AlexaUtils.newSpeechletResponse( card, speech, session, false);
	}

	@Override
	public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
		// TODO Auto-generated method stub

	}

}
