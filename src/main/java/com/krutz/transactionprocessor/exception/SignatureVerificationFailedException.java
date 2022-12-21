package com.krutz.transactionprocessor.exception;

public class SignatureVerificationFailedException extends ValidationException {

	public SignatureVerificationFailedException(String message) {
		super(message);
	}
}
