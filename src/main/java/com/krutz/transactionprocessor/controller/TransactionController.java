package com.krutz.transactionprocessor.controller;

import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.TransactionResponse;
import com.krutz.transactionprocessor.exception.ValidationException;
import com.krutz.transactionprocessor.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping(value = "/process", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<TransactionResponse> processTransaction(
			@RequestBody MerchantTransactionRequest request) {
		log.info("processTransaction invoked.");
		TransactionResponse transactionResponse = transactionService.processRequest(
				request);
		ResponseEntity responseEntity = new ResponseEntity(transactionResponse, HttpStatus.OK);
		return responseEntity;
	}
}
