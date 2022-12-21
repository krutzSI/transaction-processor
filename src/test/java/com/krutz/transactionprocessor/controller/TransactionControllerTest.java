package com.krutz.transactionprocessor.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.TransactionResponse;
import com.krutz.transactionprocessor.service.TransactionService;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import org.apache.commons.validator.routines.CurrencyValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

	@InjectMocks
	private TransactionController controller;

	@Mock
	private TransactionService transactionService;

	@Test
	public void testProcessTransaction(){
		MerchantTransactionRequest transactionRequest=new MerchantTransactionRequest();

		TransactionResponse transactionResponse = new TransactionResponse();
		Mockito.when(transactionService.processRequest(transactionRequest, new HashMap())).thenReturn(transactionResponse);

		ResponseEntity<TransactionResponse> transactionResponseResponseEntity = controller.processTransaction(
				transactionRequest, new HashMap<>());

		assertNotNull("ResponseEntity should not be null", transactionResponseResponseEntity);
		assertEquals("transactionResponse should match",transactionResponse, transactionResponseResponseEntity.getBody());


	}
}
