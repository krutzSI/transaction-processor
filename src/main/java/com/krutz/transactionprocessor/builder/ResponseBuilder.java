package com.krutz.transactionprocessor.builder;

import com.krutz.transactionprocessor.constant.Status;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.ErrorResponse;
import com.krutz.transactionprocessor.dto.response.TransactionResponse;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResponseBuilder {

	public TransactionResponse buildResponse(MerchantTransactionRequest request, UUID transactionId,
			Status status) {
		TransactionResponse transactionResponse = new TransactionResponse();
		transactionResponse.setTransactionId(String.valueOf(transactionId));
		transactionResponse.setAmount(request.getTransactionAmount());
		transactionResponse.setDate(LocalDateTime.now());
		transactionResponse.setMerchantId(request.getMerchantId());
		transactionResponse.setMerchantOrderId(request.getMerchantOrderId());
		transactionResponse.setStatus(status);
		return transactionResponse;
	}

	public ErrorResponse buildResponseForValidationError(MerchantTransactionRequest request,
			UUID transactionId, Status status, String remarks) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTransactionId(String.valueOf(transactionId));
		errorResponse.setMerchantId(request.getMerchantId());
		errorResponse.setMerchantOrderId(request.getMerchantOrderId());
		errorResponse.setStatus(status);
		errorResponse.setRemarks(remarks);
		return errorResponse;
	}
}
