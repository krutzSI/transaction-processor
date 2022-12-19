package com.krutz.transactionprocessor.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.krutz.transactionprocessor.constant.Status;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.ErrorResponse;
import com.krutz.transactionprocessor.dto.response.TransactionResponse;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResponseBuilderTest {

	@InjectMocks
	private ResponseBuilder responseBuilder;

	@Test
	public void testBuildResponse() {
		MerchantTransactionRequest request = new MerchantTransactionRequest();
		request.setTransactionAmount(BigDecimal.TEN);
		UUID merchantId = UUID.randomUUID();
		request.setMerchantId(merchantId);
		UUID transactionId = UUID.randomUUID();
		Status status = Status.IN_PROGRESS;

		TransactionResponse transactionResponse = responseBuilder.buildResponse(request, transactionId,
				status);
		assertNotNull(transactionResponse);
		assertEquals("transactionId should match", String.valueOf(transactionId),
				transactionResponse.getTransactionId());
		assertEquals("amount should match", BigDecimal.TEN, transactionResponse.getAmount());
		assertNotNull("date should not be null", transactionResponse.getDate());
		assertEquals("merchantId should match", merchantId, transactionResponse.getMerchantId());
		assertEquals("status should match", status, status);
	}

	@Test
	public void testBuildResponseForValidationError() {
		MerchantTransactionRequest request = new MerchantTransactionRequest();
		request.setTransactionAmount(BigDecimal.TEN);
		UUID merchantId = UUID.randomUUID();
		request.setMerchantId(merchantId);
		UUID transactionId = UUID.randomUUID();
		Status status = Status.IN_PROGRESS;
		String merchantOrderId = "08225dcd-78ae-4c53-a483-4a7e567dcf6a";
		request.setMerchantOrderId(merchantOrderId);

		String transactionError = "Transaction Error";
		ErrorResponse errorResponse = responseBuilder.buildResponseForValidationError(request,
				transactionId,
				status, transactionError);
		assertNotNull(errorResponse);
		assertEquals("transactionId should match", String.valueOf(transactionId),
				errorResponse.getTransactionId());
		assertEquals("merchantId should match", merchantId, errorResponse.getMerchantId());
		assertEquals("merchantId should match", merchantId, errorResponse.getMerchantId());
		assertEquals("merchantOrderId should match", merchantOrderId, errorResponse.getMerchantOrderId());
		assertEquals("status should match", status, status);
		assertEquals("remarks should match", transactionError, errorResponse.getRemarks());
	}
}
