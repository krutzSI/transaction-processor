package com.krutz.transactionprocessor.validator;

import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.exception.ValidationException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionRequestValidator {

	@Autowired
	private List<Validator> validators;

	public void validate(MerchantTransactionRequest request) {
		for (Validator validator : validators) {
			try {
				validator.validate(request);
			} catch (ValidationException e) {
				throw e;
			}
		}
	}
}
