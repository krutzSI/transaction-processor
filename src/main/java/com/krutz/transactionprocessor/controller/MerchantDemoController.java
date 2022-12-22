package com.krutz.transactionprocessor.controller;

import com.krutz.transactionprocessor.dto.response.StatusUpdateWebhookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/demo/merchant")
@Slf4j
public class MerchantDemoController {

	@PostMapping(value = "/receive/webhook",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void demoWebhookReceive(@RequestBody StatusUpdateWebhookResponse webhhok){
		log.info("Webhook received : {}",webhhok);
	}
}
