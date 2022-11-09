package com.telecom.applidistribuees.utils;

import lombok.Data;

@Data
public class ErrorMessage {
	
	private String field;
	private String message;
	
	public ErrorMessage(String field, String message) {
		this.field = field;
		this.message = message;
	}
	
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
