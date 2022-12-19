package com.krutz.transactionprocessor.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
	ACCEPTED("Accepted"),
	VALIDATION_PROCESSING("Processing validation"),
	IN_PROGRESS("In Progress"),
	FAILED("Failed"),
	SUCCESS("Successful");

	@JsonValue
	private String description;

	Status(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
