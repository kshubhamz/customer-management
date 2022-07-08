package com.redhatschool.customermanagement.config;

import java.time.Instant;

public class ExceptionResponse {
	private String message;
	private final Instant timeStamp;

	public ExceptionResponse() {
		this.timeStamp = Instant.now();
	}

	public ExceptionResponse(String message) {
		this.message = message;
		this.timeStamp = Instant.now();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Instant getTimeStamp() {
		return timeStamp;
	}

}
