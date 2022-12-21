package com.krutz.transactionprocessor.validator;

import com.krutz.transactionprocessor.dao.model.MerchantDetailsDO;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.exception.ValidationException;
import com.krutz.transactionprocessor.service.MerchantService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class TransactionRequestValidator {

	@Autowired
	private List<Validator> validators;

	@Autowired
	private MerchantService merchantService;

	public void validate(MerchantTransactionRequest request) {
		MerchantDetailsDO merchantDetailsDO = merchantService.getMerchantByMerchantId(
				request.getMerchantId());
		for (Validator validator : validators) {
			try {
				validator.validate(request,merchantDetailsDO);
			} catch (ValidationException e) {
				throw e;
			}
		}
	}
}
