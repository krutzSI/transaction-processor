package com.krutz.transactionprocessor.exception;

import com.krutz.transactionprocessor.dto.response.ErrorResponse;

public class ValidationException extends RuntimeException {
	private ErrorResponse errorResponse;
	public ValidationException(String message) {
		super(message);
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}
}
