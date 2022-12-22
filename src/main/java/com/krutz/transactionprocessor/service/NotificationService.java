package com.krutz.transactionprocessor.service;

import com.krutz.transactionprocessor.builder.ResponseBuilder;
import com.krutz.transactionprocessor.dao.model.MerchantDetailsDO;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import com.krutz.transactionprocessor.dto.response.StatusUpdateWebhookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class NotificationService {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private ResponseBuilder responseBuilder;
	@Autowired
	private HMACSignatureService signatureService;

	public void notifyTransactionStatusToMerchant(TransactionRequestDetailsDO transaction) {
		log.info("notifyMerchant invoked for transactionId  : {}", transaction.getTransactionId());
		MerchantDetailsDO merchantDetails = merchantService.getMerchantByMerchantId(
				transaction.getMerchantId());

		StatusUpdateWebhookResponse response = responseBuilder.buildWebhookResponse(transaction);
		response.setSignature(
				signatureService.generateSignatureForWebhook(response, merchantDetails.getSecretKey()));

		HttpEntity<StatusUpdateWebhookResponse> entity = new HttpEntity(
				response);

		try {
			restTemplate.exchange(
							merchantDetails.getMerchantWebhookEndpoint(), HttpMethod.POST, entity, String.class)
					.getBody();
		} catch (Exception exception) {
			log.error("notifyMerchant failed for merchantId : {}, transactionId : {}",
					transaction.getMerchantId(), transaction.getMerchantId(), exception);
		}
	}
}
