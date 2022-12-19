package com.krutz.transactionprocessor.exception;

public class DuplicateTransactionException extends ValidationException{

	public DuplicateTransactionException(String message) {
		super(message);
	}
}

