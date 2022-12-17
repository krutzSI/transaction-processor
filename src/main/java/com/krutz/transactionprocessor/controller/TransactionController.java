package com.krutz.transactionprocessor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TransactionController {

	@GetMapping(value = "test", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity test(){
		log.info("test controller");
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
}
