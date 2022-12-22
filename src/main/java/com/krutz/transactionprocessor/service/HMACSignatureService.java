package com.krutz.transactionprocessor.service;

import com.google.common.hash.Hashing;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.StatusUpdateWebhookResponse;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class HMACSignatureService {

	private static final String PAYLOAD_SEPERATOR = ":";

	public boolean verifySignature(MerchantTransactionRequest request, String secret) {

		String generatedSignature = generatedSignature(generatePayloadForRequest(request), secret);
		if (StringUtils.equals(generatedSignature, request.getSignature())) {
			return true;
		}
		return false;
	}

	public String generateSignatureForWebhook(StatusUpdateWebhookResponse response, String secret) {
		StringBuilder payloadBuilder = new StringBuilder();
		payloadBuilder.append(response.getMerchantId());
		payloadBuilder.append(PAYLOAD_SEPERATOR);
		payloadBuilder.append(response.getMerchantOrderId());
		payloadBuilder.append(PAYLOAD_SEPERATOR);
		payloadBuilder.append(response.getTransactionId());
		payloadBuilder.append(PAYLOAD_SEPERATOR);
		payloadBuilder.append(response.getStatus());
		payloadBuilder.append(PAYLOAD_SEPERATOR);
		payloadBuilder.append(response.getAmount());
		payloadBuilder.append(PAYLOAD_SEPERATOR);
		payloadBuilder.append(response.getCurrency());
		return generatedSignature(payloadBuilder.toString(), secret);

	}

	private String generatedSignature(String payload, String secret) {
		return Hashing.hmacSha256(secret.getBytes(StandardCharsets.UTF_8))
				.hashString(payload, StandardCharsets.UTF_8).toString();
	}

	private String generatePayloadForRequest(MerchantTransactionRequest request) {

		StringBuilder payloadBuilder = new StringBuilder();
		payloadBuilder.append(request.getMerchantId());
		payloadBuilder.append(PAYLOAD_SEPERATOR);
		payloadBuilder.append(request.getMerchantOrderId());
		payloadBuilder.append(PAYLOAD_SEPERATOR);
		payloadBuilder.append(request.getTransactionAmount().toString());
		payloadBuilder.append(PAYLOAD_SEPERATOR);
		payloadBuilder.append(request.getCurrency());

		return payloadBuilder.toString();
	}
}
