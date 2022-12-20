package com.krutz.transactionprocessor.exception;

public class TransactionNotFoundException extends RuntimeException{

	public TransactionNotFoundException(String message) {
		super(message);
	}
}
