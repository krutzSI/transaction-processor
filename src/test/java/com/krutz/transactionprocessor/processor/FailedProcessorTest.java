package com.krutz.transactionprocessor.processor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

import com.krutz.transactionprocessor.builder.ResponseBuilder;
import com.krutz.transactionprocessor.constant.Status;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.TransactionResponse;
import com.krutz.transactionprocessor.service.TransactionRequestService;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FailedProcessorTest {


	@InjectMocks
	private FailedProcessor failedProcessor;
	@Mock
	private ResponseBuilder responseBuilder;
	@Mock
	private TransactionRequestService requestService;

	@Test
	public void whenProcessCalledThenSaveTransactionInFailedStatus() {
		MerchantTransactionRequest request = new MerchantTransactionRequest();
		TransactionRequestDetailsDO requestDetailsDO = new TransactionRequestDetailsDO();
		UUID transactionId = UUID.randomUUID();
		requestDetailsDO.setTransactionId(transactionId);
		TransactionResponse response = new TransactionResponse();
		response.setTransactionId(transactionId);
		response.setStatus(Status.FAILED);
		Mockito.when(responseBuilder.buildResponse(request,
				transactionId,
				Status.FAILED)).thenReturn(response);

		TransactionResponse actualResponse = failedProcessor.process(request, requestDetailsDO);
		assertEquals("response should match", response, actualResponse);
		Mockito.verify(requestService, times(1)).updateStatus(requestDetailsDO, Status.FAILED);
	}

}
