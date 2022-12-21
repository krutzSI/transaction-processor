package com.krutz.transactionprocessor.processor;

import com.krutz.transactionprocessor.constant.Status;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class DefaultProcessor extends TransactionProcessor {

	@Override
	public TransactionResponse process(MerchantTransactionRequest request,
			TransactionRequestDetailsDO requestDetailsDO) {
		TransactionResponse transactionResponse = responseBuilder.buildResponse(request,
				requestDetailsDO.getTransactionId(),
				Status.IN_PROGRESS);
		//persist status
		requestService.updateStatus(requestDetailsDO, transactionResponse.getStatus());
		return transactionResponse;
	}

}
