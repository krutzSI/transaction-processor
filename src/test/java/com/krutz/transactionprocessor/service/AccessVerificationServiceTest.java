package com.krutz.transactionprocessor.service;

import com.krutz.transactionprocessor.dao.model.MerchantDetailsDO;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import com.krutz.transactionprocessor.exception.UnauthorizedAccessException;
import com.krutz.transactionprocessor.service.AccessVerificationService;
import com.krutz.transactionprocessor.service.MerchantService;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class AccessVerificationServiceTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	@InjectMocks
	private AccessVerificationService accessVerificationService;
	@Mock
	private MerchantService merchantService;

	@Before
	public void init() {
		ReflectionTestUtils.setField(accessVerificationService, "principalRequestHeader", "x-api-key");
	}

	@Test
	public void whenRequestIsValidDoNothing() {

		Map requestHeader = new HashMap(1);
		UUID mockApiKey = UUID.randomUUID();
		UUID mockedMerchantId = UUID.randomUUID();
		requestHeader.put("x-api-key", mockApiKey.toString());

		TransactionRequestDetailsDO requestDetailsDO = new TransactionRequestDetailsDO();
		requestDetailsDO.setMerchantId(mockedMerchantId);

		MerchantDetailsDO merchant = new MerchantDetailsDO();
		merchant.setMerchantId(mockedMerchantId);
		Mockito.when(merchantService.getMerchantByApiKey(mockApiKey)).thenReturn(merchant);

		accessVerificationService.verifyAccessToResource(requestHeader, requestDetailsDO);

		Mockito.verify(merchantService, Mockito.times(1)).getMerchantByApiKey(mockApiKey);
	}


	@Test
	public void whenRequestIsInValidThrowException() {
		exception.expect(UnauthorizedAccessException.class);

		Map requestHeader = new HashMap(1);
		UUID mockApiKey = UUID.randomUUID();
		UUID mockedMerchantId = UUID.randomUUID();
		requestHeader.put("x-api-key", mockApiKey.toString());

		TransactionRequestDetailsDO requestDetailsDO = new TransactionRequestDetailsDO();
		requestDetailsDO.setMerchantId(mockedMerchantId);

		MerchantDetailsDO merchant = new MerchantDetailsDO();
		merchant.setMerchantId(UUID.randomUUID());
		Mockito.when(merchantService.getMerchantByApiKey(mockApiKey)).thenReturn(merchant);

		accessVerificationService.verifyAccessToResource(requestHeader, requestDetailsDO);
	}

}
