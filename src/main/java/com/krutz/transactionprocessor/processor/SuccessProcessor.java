package com.krutz.transactionprocessor.processor;

import com.krutz.transactionprocessor.constant.Status;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.TransactionResponse;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class SuccessProcessor extends TransactionProcessor {

	@Override
	public TransactionResponse process(MerchantTransactionRequest request, UUID transactionId) {
		TransactionResponse transactionResponse = responseBuilder.buildResponse(request, transactionId,
				Status.SUCCESS);
		return transactionResponse;
	}

}
