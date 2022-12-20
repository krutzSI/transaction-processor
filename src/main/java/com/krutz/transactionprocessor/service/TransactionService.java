package com.krutz.transactionprocessor.service;

import com.krutz.transactionprocessor.builder.ResponseBuilder;
import com.krutz.transactionprocessor.constant.Status;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import com.krutz.transactionprocessor.dto.response.TransactionDetailsResponse;
import com.krutz.transactionprocessor.dto.response.TransactionResponse;
import com.krutz.transactionprocessor.exception.TransactionNotFoundException;
import com.krutz.transactionprocessor.exception.ValidationException;
import com.krutz.transactionprocessor.processor.ProcessorFactory;
import com.krutz.transactionprocessor.validator.TransactionRequestValidator;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionService {

	@Autowired
	private TransactionRequestService requestService;

	@Autowired
	private TransactionRequestValidator validator;

	@Autowired
	private ProcessorFactory processorFactory;

	@Autowired
	private ResponseBuilder responseBuilder;

	public TransactionResponse processRequest(MerchantTransactionRequest request) {
		log.info("processTransaction invoked for merchantId: {}, merchantOrderId : {}",
				request.getMerchantId(), request.getMerchantOrderId());
		//save request
		TransactionRequestDetailsDO requestDO = requestService.persistRequest(
				request);

		//validate request
		validate(request, requestDO);

		log.info("generated transactionReference : {}  for merchantId: {}, merchantOrderId : {}",
				requestDO.getTransactionId(), request.getMerchantId(), request.getMerchantOrderId());

		//invoke processing logic
		TransactionResponse response = processorFactory.getProcessor(request.getTransactionAmount())
				.process(request, requestDO.getTransactionId());

		//persist status
		requestService.updateStatus(requestDO, response.getStatus());

		//return response
		return response;
	}

	private void validate(MerchantTransactionRequest request,
			TransactionRequestDetailsDO requestDetailsDO) {
		try {
			requestService.updateStatus(requestDetailsDO, Status.VALIDATION_PROCESSING);
			validator.validate(request);
		} catch (ValidationException exception) {
			log.error("Validation failed for transactionsReference : {}",
					requestDetailsDO.getTransactionId());
			requestService.updateStatus(requestDetailsDO, Status.FAILED);

			exception.setErrorResponse(responseBuilder.buildResponseForValidationError(request,
					requestDetailsDO.getTransactionId(), Status.FAILED, exception.getMessage()));

			throw exception;
		}
	}

	public TransactionDetailsResponse getTransactionByTransactionId(UUID transactionId) {

		Optional<TransactionRequestDetailsDO> transactionRequestDetailsDO = requestService.findByTransactionId(
				transactionId);
		if (transactionRequestDetailsDO.isPresent()) {
			return responseBuilder.buildForTransactionDetails(transactionRequestDetailsDO.get());
		}else{
			throw new TransactionNotFoundException("No TransactionFound with transactionId "+transactionId);
		}
	}

	public List<TransactionDetailsResponse> getTransactionByMerchantId(UUID merchantId,
			LocalDate transactionDate) {

		List<TransactionRequestDetailsDO> transactionRequestDetailsDO = requestService.findByMerchantIdAndTransactionDate(
				merchantId, transactionDate);
		return responseBuilder.buildForTransactionDetails(transactionRequestDetailsDO);
	}

}
