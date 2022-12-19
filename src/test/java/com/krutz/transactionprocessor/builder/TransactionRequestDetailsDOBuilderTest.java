package com.krutz.transactionprocessor.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.krutz.transactionprocessor.constant.Status;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionRequestDetailsDOBuilderTest {

	@InjectMocks
	private TransactionRequestDetailsDOBuilder transactionRequestDetailsDOBuilder;

	@Test
	public void testBuild() {
		MerchantTransactionRequest transactionRequest = new MerchantTransactionRequest();
		transactionRequest.setTransactionAmount(BigDecimal.TEN);
		TransactionRequestDetailsDO requestDetailsDO = transactionRequestDetailsDOBuilder.build(
				transactionRequest);
		assertNotNull("TransactionRequestDetailsDO should not null", requestDetailsDO);
		assertNotNull("TransactionReference should not null",requestDetailsDO.getTransactionReference());
		assertEquals("Status should match", Status.ACCEPTED,requestDetailsDO.getStatus());
	}
}
