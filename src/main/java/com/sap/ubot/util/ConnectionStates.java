package com.sap.ubot.util;

public enum ConnectionStates {
	
	CONNECTED(1),TEMP_DISCONNECTED(2),DISCONNECTED(3);
	
	private int state;
	
	ConnectionStates(int state) {
		this.state = state;
	}
	
	public int getState() {
		return this.state;
	}

}
