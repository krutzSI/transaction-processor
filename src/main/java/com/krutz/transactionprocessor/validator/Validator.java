package com.krutz.transactionprocessor.validator;

import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.exception.DuplicateTransactionException;
import com.krutz.transactionprocessor.exception.RequestValidationException;

public interface Validator {

	boolean validate(MerchantTransactionRequest request)
			throws RequestValidationException, DuplicateTransactionException;

}
