package com.krutz.transactionprocessor.service;

import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HMACSignatureServiceTest {

	private MerchantTransactionRequest getRequest(){
		MerchantTransactionRequest request = new MerchantTransactionRequest();

		request.setMerchantId(UUID.fromString("858ed021-c926-454f-bc87-f40d2089f32e"));
		request.setMerchantOrderId("7903de61-6e23-4cf4-83a9-f395a919e798");
		request.setTransactionAmount(BigDecimal.valueOf(12.45));
		request.setCurrency("USD");


		return request;
	}
}
