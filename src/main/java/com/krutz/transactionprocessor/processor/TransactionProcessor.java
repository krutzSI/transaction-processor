package com.krutz.transactionprocessor.processor;

import com.krutz.transactionprocessor.builder.ResponseBuilder;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.TransactionResponse;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class TransactionProcessor {

	@Autowired
	protected ResponseBuilder responseBuilder;
	public abstract TransactionResponse process(MerchantTransactionRequest request, UUID transactionId);

}
