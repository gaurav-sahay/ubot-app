package com.sap.ubot.recast.ai;

import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecastAiClient {
	
	private static final String BOT_REQUEST_TOKEN = "bfe1f459027e5bf2d0a7271b9c9fcf47";
	
	/*public static void main(String[] args) {
		try {
			chatWithBot("CONVERSATION_START");
			chatWithBot("hi");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public static String chatWithBot(String text) throws JSONException {
		
		Request request = new Request(BOT_REQUEST_TOKEN);
		//Conversation conversation = request.doTextConverse(text);
		String response = request.doDialogRequest("{" + 
				"\"message\" :" + 
				"	{" + 
				"	\"type\": \"text\"," + 
				"	\"content\": \""+text+"\"" + 
				"	} ," + 
				"\"conversation_id\" :\"1234567890123121121\"" + 
				"}", null, null);
		 JSONObject	result = new JSONObject(response);
		 JSONArray messages = result.getJSONObject("results").getJSONArray("messages");
		 System.out.println(((JSONObject)messages.get(0)).get("value"));
		 return ((JSONObject)messages.get(0)).get("value").toString();
		
	}
	
	
}
