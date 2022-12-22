package com.krutz.transactionprocessor.processor;

import com.krutz.transactionprocessor.constant.Status;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.TransactionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultProcessor extends TransactionProcessor {

	@Override
	public TransactionResponse process(MerchantTransactionRequest request,
			TransactionRequestDetailsDO requestDetailsDO) {
		TransactionResponse transactionResponse = responseBuilder.buildResponse(request,
				requestDetailsDO.getTransactionId(),
				Status.IN_PROGRESS);
		//persist status
		requestService.updateStatus(requestDetailsDO, transactionResponse.getStatus());
		log.info("transaction : {} status updated to : {}", requestDetailsDO.getTransactionId(),
				transactionResponse.getStatus());
		return transactionResponse;
	}

}
