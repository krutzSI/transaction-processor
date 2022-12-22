package com.krutz.transactionprocessor.validator;

import com.krutz.transactionprocessor.dao.model.MerchantDetailsDO;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.exception.RequestValidationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.ConstraintViolation;
import liquibase.repackaged.org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class BeanValidator implements Validator {

	@Autowired
	private javax.validation.Validator validator;

	@Override
	public boolean validate(MerchantTransactionRequest request, MerchantDetailsDO merchantDetailsDO) {
		Set<ConstraintViolation<MerchantTransactionRequest>> violations = validator.validate(request);
		StringBuilder validationErrorBuffer = new StringBuilder();

		if (CollectionUtils.isNotEmpty(violations)) {
			for (ConstraintViolation<MerchantTransactionRequest> violation : violations) {
				validationErrorBuffer.append(violation.getPropertyPath());
				validationErrorBuffer.append(" ");
				validationErrorBuffer.append(violation.getMessage());
				validationErrorBuffer.append(", ");
			}
		}

		validationErrorBuffer.append(validateTransactionDate(request.getTransactionDate()));

		if (StringUtils.isNotBlank(validationErrorBuffer.toString())) {
			throw new RequestValidationException(validationErrorBuffer.toString());
		}

		return Boolean.TRUE;
	}

	private String validateTransactionDate(LocalDateTime transactionDate) {
		if (transactionDate.isBefore(LocalDateTime.of(LocalDate.now(), LocalDateTime.MIN.toLocalTime())))
		{
			return "transactionDate must be greater than or equal to current_date";
		}
		return StringUtils.EMPTY;
	}

}
