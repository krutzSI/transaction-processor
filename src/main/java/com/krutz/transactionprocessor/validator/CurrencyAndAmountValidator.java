package com.krutz.transactionprocessor.validator;

import com.krutz.transactionprocessor.dao.model.MerchantDetailsDO;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.exception.ValidationException;
import com.krutz.transactionprocessor.service.MerchantService;
import java.util.Currency;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
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
		checkIfCurrencySupported(request, merchantDetailsDO);
		checkAmountFormatForCurrency(request);
		return true;
	}

	private void checkAmountFormatForCurrency(MerchantTransactionRequest request) {
		String DECIMAL_SYMBOL = ".";
		String[] split = StringUtils.split(request.getTransactionAmount().toString(), DECIMAL_SYMBOL);
		if (split.length > 1) {
			int defaultFractionDigits = Currency.getInstance(request.getCurrency())
					.getDefaultFractionDigits();
			if (StringUtils.replace(split[1], "0", "").length() != 0
					&& split[1].length() > defaultFractionDigits) {
				throw new ValidationException(
						"amount format incorrect for currency : " + request.getCurrency());
			}
		}
	}

	private void checkIfCurrencySupported(MerchantTransactionRequest request,
			MerchantDetailsDO merchantDetailsDO) {
		List<String> supportedCurrencyList = merchantService.getSupportedCurrencyMerchantId(
				merchantDetailsDO.getId());
		if (!supportedCurrencyList.contains(request.getCurrency())) {
			throw new ValidationException("currency not supported.");
		}
	}
}
