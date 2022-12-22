package com.krutz.transactionprocessor.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.krutz.transactionprocessor.builder.ResponseBuilder;
import com.krutz.transactionprocessor.constant.Status;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.TransactionResponse;
import com.krutz.transactionprocessor.processor.DefaultProcessor;
import com.krutz.transactionprocessor.processor.ProcessorFactory;
import com.krutz.transactionprocessor.validator.TransactionRequestValidator;
import java.math.BigDecimal;
import java.util.HashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

	@InjectMocks
	private TransactionService transactionService;

	@Mock
	private TransactionRequestService requestService;

	@Mock
	private TransactionRequestValidator validator;

	@Mock
	private ProcessorFactory processorFactory;

	@Mock
	private ResponseBuilder responseBuilder;

	@Mock
	private NotificationService notificationService;

	@Mock
	private DefaultProcessor defaultProcessor;
	@Mock
	private AccessVerificationService accessVerificationService;


	@Test
	public void whenATransactionRequestSubmittedProcessAndReturnResponse() {
		MerchantTransactionRequest request = new MerchantTransactionRequest();
		HashMap requestHeader = new HashMap(1);
		request.setTransactionAmount(BigDecimal.TEN);
		TransactionRequestDetailsDO requestDetailsDO = new TransactionRequestDetailsDO();

		when(requestService.persistRequest(
				request)).thenReturn(requestDetailsDO);
		when(processorFactory.getProcessor(BigDecimal.TEN)).thenReturn(defaultProcessor);
		TransactionResponse transactionResponse = new TransactionResponse();
		when(defaultProcessor.process(request, requestDetailsDO)).thenReturn(transactionResponse);

		assertNotNull("response should not be null",
				transactionService.processRequest(request, requestHeader));
		verify(accessVerificationService, times(1)).verifyAccessToResource(requestHeader,
				requestDetailsDO);
		verify(requestService, times(1)).updateStatus(requestDetailsDO, Status.VALIDATION_PROCESSING);
		verify(validator, times(1)).validate(request);
	}
}
