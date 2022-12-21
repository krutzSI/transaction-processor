package com.krutz.transactionprocessor.validator;

import com.krutz.transactionprocessor.dao.model.MerchantDetailsDO;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.exception.DuplicateTransactionException;
import com.krutz.transactionprocessor.service.TransactionRequestService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class DuplicateRequestValidator implements Validator {

	@Autowired
	private TransactionRequestService requestService;

	@Override
	public boolean validate(MerchantTransactionRequest request, MerchantDetailsDO merchantDetailsDO) {
		TransactionRequestDetailsDO duplicateTransaction =
				requestService.findDuplicateTransaction(
						request.getMerchantId(), request.getMerchantOrderId());
		if (Objects.nonNull(duplicateTransaction)) {
			throw new DuplicateTransactionException("transaction is already processed");
		}
		return Boolean.TRUE;
	}
}
