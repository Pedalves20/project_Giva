package br.com.manager.util;

public enum Message {

	
	
	REQUEST_NO_PROCESS(555, "The request not processed");
	
	private final int value;

	private final String reasonPhrase;


	Message(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}


	public int getValue() {
		return value;
	}


	public String getReasonPhrase() {
		return reasonPhrase;
	}
	
	

}
