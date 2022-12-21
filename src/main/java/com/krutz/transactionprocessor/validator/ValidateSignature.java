package com.krutz.transactionprocessor.validator;

import com.krutz.transactionprocessor.dao.model.MerchantDetailsDO;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.exception.SignatureVerificationFailedException;
import com.krutz.transactionprocessor.service.HMACSignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ValidateSignature implements Validator {

	@Value("${signature.verification.enabled}")
	private boolean signatureVerificationEnabled;

	@Autowired
	private HMACSignatureService signatureService;

	@Override
	public boolean validate(MerchantTransactionRequest request, MerchantDetailsDO merchantDetailsDO) {
		if(signatureVerificationEnabled && !signatureService.verifySignature(request, merchantDetailsDO.getSecretKey())){
			throw new SignatureVerificationFailedException("Signature verification failed.");
		}
		return true;
	}
}
