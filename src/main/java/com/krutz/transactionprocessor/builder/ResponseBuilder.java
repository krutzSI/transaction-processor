package com.krutz.transactionprocessor.builder;

import com.krutz.transactionprocessor.constant.Status;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.ErrorResponse;
import com.krutz.transactionprocessor.dto.response.TransactionDetailsResponse;
import com.krutz.transactionprocessor.dto.response.TransactionErrorResponse;
import com.krutz.transactionprocessor.dto.response.TransactionResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResponseBuilder {

	public TransactionResponse buildResponse(MerchantTransactionRequest request, UUID transactionId,
			Status status) {
		TransactionResponse transactionResponse = new TransactionResponse();
		transactionResponse.setTransactionId(transactionId);
		transactionResponse.setAmount(request.getTransactionAmount());
		transactionResponse.setTransactionDate(request.getTransactionDate());
		transactionResponse.setMerchantId(request.getMerchantId());
		transactionResponse.setMerchantOrderId(request.getMerchantOrderId());
		transactionResponse.setStatus(status);
		return transactionResponse;
	}

	public TransactionErrorResponse buildResponseForValidationError(MerchantTransactionRequest request,
			UUID transactionId, Status status, String remarks) {
		TransactionErrorResponse errorResponse = new TransactionErrorResponse();
		errorResponse.setTransactionId(transactionId);
		errorResponse.setMerchantId(request.getMerchantId());
		errorResponse.setMerchantOrderId(request.getMerchantOrderId());
		errorResponse.setStatus(status);
		errorResponse.setRemarks(remarks);
		return errorResponse;
	}

	public TransactionDetailsResponse buildForTransactionDetails(
			TransactionRequestDetailsDO requestDetailsDO) {
		TransactionDetailsResponse response = new TransactionDetailsResponse();
		response.setTransactionDate(requestDetailsDO.getTransactionDate());
		response.setMerchantId(requestDetailsDO.getMerchantId());
		response.setAmount(requestDetailsDO.getTransactionAmount());
		response.setStatus(requestDetailsDO.getStatus());
		response.setMerchantOrderId(requestDetailsDO.getMerchantOrderId());
		response.setTransactionId(requestDetailsDO.getTransactionId());
		response.setShopperInfo(requestDetailsDO.getShopperInfo());

		return response;
	}

	public List<TransactionDetailsResponse> buildForTransactionDetails(
			List<TransactionRequestDetailsDO> requestDetailsDOList) {
		List<TransactionDetailsResponse> response = new ArrayList(requestDetailsDOList.size());
		requestDetailsDOList.forEach(
				requestDetailsDO -> response.add(buildForTransactionDetails(requestDetailsDO)));
		return response;
	}
}
