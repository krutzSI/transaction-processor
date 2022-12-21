package com.krutz.transactionprocessor.service;

import com.krutz.transactionprocessor.dao.model.MerchantDetailsDO;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import com.krutz.transactionprocessor.exception.UnauthorizedAccessException;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccessVerificationService {

	@Value("${http.auth-token-header-name}")
	private String principalRequestHeader;

	@Autowired
	private MerchantService merchantService;

	public void verifyAccessToResource(Map requestHeader,
			TransactionRequestDetailsDO requestDetailsDO) {
		String apiKey = (String) requestHeader.get(principalRequestHeader);
		MerchantDetailsDO merchant = merchantService.getMerchantByApiKey(UUID.fromString(apiKey));
		validateMerchantId(requestDetailsDO.getMerchantId(), merchant.getMerchantId());
	}

	private void validateMerchantId(UUID requestMerchantId, UUID originalMerchantId) {
		if (!requestMerchantId.equals(originalMerchantId)) {
			throw new UnauthorizedAccessException("Access denied to requested resource.");
		}
	}

}
