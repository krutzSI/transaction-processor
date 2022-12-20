package com.krutz.transactionprocessor.controller;

import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.TransactionDetailsResponse;
import com.krutz.transactionprocessor.dto.response.TransactionResponse;
import com.krutz.transactionprocessor.service.TransactionService;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping(value = "/details",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TransactionResponse> getTransactionDetails(
			@RequestParam(name = "transactionId") UUID transactionId) {
		log.info("getTransactionDetails invoked for {}",transactionId);
		TransactionDetailsResponse transactionDetails = transactionService.getTransactionByTransactionId(
				transactionId);
		ResponseEntity responseEntity = new ResponseEntity(transactionDetails, HttpStatus.OK);
		return responseEntity;
	}


	@GetMapping(value = "/allDetails",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TransactionResponse>> getAllTransactionDetails(
			@RequestParam(name = "merchantId") UUID merchantId,@RequestParam(name = "transactionDate")
			@DateTimeFormat(iso = ISO.DATE) LocalDate transactionDate) {
		log.info("getAllTransactionDetails invoked for {}",merchantId, " transactionDate : {}",transactionDate);
		List<TransactionDetailsResponse> transactionDetails = transactionService.getTransactionByMerchantId(
				merchantId,transactionDate);
		ResponseEntity responseEntity = new ResponseEntity(transactionDetails, HttpStatus.OK);
		return responseEntity;
	}
}
