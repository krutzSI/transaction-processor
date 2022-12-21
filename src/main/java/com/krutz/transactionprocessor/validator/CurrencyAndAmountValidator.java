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
@Order(4)
public class CurrencyAndAmountValidator implements Validator {

	@Autowired
	private MerchantService merchantService;

	@Override
	public boolean validate(MerchantTransactionRequest request, MerchantDetailsDO merchantDetailsDO) {
		List<String> supportedCurrencyList = merchantService.getSupportedCurrencyMerchantId(
				merchantDetailsDO.getId());
		if (!supportedCurrencyList.contains(request.getCurrency())) {
			throw new ValidationException("currency not supported.");
		}
		return true;
	}
}
