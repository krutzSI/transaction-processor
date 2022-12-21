package com.krutz.transactionprocessor.service;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.exception.SignatureVerificationFailedException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class HMACSignatureService {

	private static final String PAYLOAD_SEPERATOR = ":";

	public boolean verifySignature(MerchantTransactionRequest request, String secret) {

		String generatedSignature = generatedSignature(generatePayload(request), secret);
		if(StringUtils.equals(generatedSignature,request.getSignature())){
			return true;
		}
		return  false;
	}

	private String generatedSignature(String payload, String secret) {
		return Hashing.hmacSha256(secret.getBytes(StandardCharsets.UTF_8))
				.hashString(payload, StandardCharsets.UTF_8).toString();
	}

	private String generatePayload(MerchantTransactionRequest request) {

		return request.getMerchantId() + PAYLOAD_SEPERATOR
				+ request.getMerchantOrderId() + PAYLOAD_SEPERATOR + request.getTransactionAmount().toString()
				+ PAYLOAD_SEPERATOR
				+ request.getCurrency();
	}

	public static void main(String[] args) {
		String s = "d6a0504b-b304-4e16-837f-9cab6b1f9b6c" + PAYLOAD_SEPERATOR
				+ "if85a37a-k285-451f-9c30-cc026834e9bd" + PAYLOAD_SEPERATOR + "200"
				+ PAYLOAD_SEPERATOR
				+ "MYR";
		System.out.println(new HMACSignatureService().generatedSignature(s,"9418dd5a-9d0c-4b52-95ec-8dd7aebc4222"));
	}
}
