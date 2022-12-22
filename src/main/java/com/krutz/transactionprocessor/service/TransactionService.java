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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionService {

	@Autowired
	AccessVerificationService accessVerificationService;

	@Autowired
	private TransactionRequestService requestService;

	@Autowired
	private TransactionRequestValidator validator;

	@Autowired
	private ProcessorFactory processorFactory;

	@Autowired
	private ResponseBuilder responseBuilder;

	@Autowired
	private NotificationService notificationService;


	public TransactionResponse processRequest(MerchantTransactionRequest request, Map requestHeader) {
		log.info("processTransaction invoked for merchantId: {}, merchantOrderId : {}",
				request.getMerchantId(), request.getMerchantOrderId());
		//save request
		TransactionRequestDetailsDO requestDO = requestService.persistRequest(
				request);

		//validate access
		accessVerificationService.verifyAccessToResource(requestHeader, requestDO);

		//validate request
		validate(request, requestDO);

		log.info("generated transactionReference : {}  for merchantId: {}, merchantOrderId : {}",
				requestDO.getTransactionId(), request.getMerchantId(), request.getMerchantOrderId());

		//invoke processing logic
		TransactionResponse response = processorFactory.getProcessor(request.getTransactionAmount())
				.process(request, requestDO);

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

	public TransactionDetailsResponse getTransactionByTransactionId(UUID transactionId,
			Map requestHeader) {
		log.info("getTransactionByMerchantId invoked for transactionId : {}",
				transactionId);
		Optional<TransactionRequestDetailsDO> transactionRequestDetailsDO = requestService.findByTransactionId(
				transactionId);
		if (transactionRequestDetailsDO.isPresent()) {
			TransactionRequestDetailsDO requestDetailsDO = transactionRequestDetailsDO.get();
			accessVerificationService.verifyAccessToResource(requestHeader, requestDetailsDO);
			return responseBuilder.buildForTransactionDetails(requestDetailsDO);
		} else {
			throw new TransactionNotFoundException(
					"No TransactionFound with transactionId " + transactionId);
		}
	}

	public List<TransactionDetailsResponse> getTransactionByMerchantId(UUID merchantId,
			LocalDate transactionDate) {
		log.info("getTransactionByMerchantId invoked for merchantId : {}, transactionDate : {}",
				merchantId, transactionDate);
		List<TransactionRequestDetailsDO> transactionRequestDetailsDO = requestService.findByMerchantIdAndTransactionDate(
				merchantId, transactionDate);
		return responseBuilder.buildForTransactionDetails(transactionRequestDetailsDO);
	}

	/**
	 * Change status of transaction with amount 100 to SUCCESS and amount 300 to FAILED and notify
	 * merchant
	 */
	public void processInProgressTransactions() {
		log.info("processInProgressTransactions invoked");
		List<TransactionRequestDetailsDO> transactionsToBeProcessed = requestService.getAllInProgressTransactions();

		if (CollectionUtils.isEmpty(transactionsToBeProcessed)) {
			log.info("No IN_PROGRESS transactions found");
			return;
		}

		transactionsToBeProcessed.stream().forEach(transaction -> {
			try {
				if (transaction.getTransactionAmount().equals(BigDecimal.valueOf(100))) {
					log.info("transaction : {} is successfully processed", transaction.getTransactionId());
					updateAndNotify(transaction, Status.SUCCESS);
				} else if (transaction.getTransactionAmount().equals(BigDecimal.valueOf(200))) {
					log.info("transaction : {} is failed to process", transaction.getTransactionId());
					updateAndNotify(transaction, Status.FAILED);
				} else {
					log.info("transaction : {} is yet to be processed", transaction.getTransactionId());
				}
			} catch (Exception e) {
				log.error("Exception processing transaction : {} ", transaction.getTransactionId(), e);
			}
		});

	}

	private void updateAndNotify(TransactionRequestDetailsDO transaction, Status status) {
		requestService.updateStatus(transaction, status);
		notificationService.notifyTransactionStatusToMerchant(transaction);
	}

}
