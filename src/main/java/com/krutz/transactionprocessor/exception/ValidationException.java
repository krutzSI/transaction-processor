package com.krutz.transactionprocessor.exception;

import com.krutz.transactionprocessor.dto.response.TransactionErrorResponse;

public class ValidationException extends RuntimeException {
	private TransactionErrorResponse errorResponse;
	public ValidationException(String message) {
		super(message);
	}

	public TransactionErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(TransactionErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}
}
