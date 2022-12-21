package com.krutz.transactionprocessor.processor;

import com.krutz.transactionprocessor.builder.ResponseBuilder;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.TransactionResponse;
import com.krutz.transactionprocessor.service.TransactionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class TransactionProcessor {

	@Autowired
	protected ResponseBuilder responseBuilder;


	@Autowired
	protected TransactionRequestService requestService;

	public abstract TransactionResponse process(MerchantTransactionRequest request,
			TransactionRequestDetailsDO requestDetailsDO);

}
