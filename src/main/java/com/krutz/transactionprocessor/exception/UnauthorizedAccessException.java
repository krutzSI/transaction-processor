package com.krutz.transactionprocessor.exception;

public class UnauthorizedAccessException extends RuntimeException{

	public UnauthorizedAccessException(String message) {
		super(message);
	}
}
