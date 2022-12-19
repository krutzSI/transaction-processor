package com.krutz.transactionprocessor.service;

import com.krutz.transactionprocessor.builder.TransactionRequestDetailsDOBuilder;
import com.krutz.transactionprocessor.constant.Status;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import com.krutz.transactionprocessor.dao.repository.TransactionRequestDetailsRepo;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionRequestService {

	private static final List<Status> PROCESSED_STATUS = Collections.unmodifiableList(
			Arrays.asList(Status.ACCEPTED, Status.IN_PROGRESS, Status.SUCCESS));

	@Autowired
	private TransactionRequestDetailsDOBuilder transactionRequestDetailsDOBuilder;
	@Autowired
	private TransactionRequestDetailsRepo requestDetailsRepo;

	public TransactionRequestDetailsDO persistRequest(MerchantTransactionRequest request) {
		TransactionRequestDetailsDO requestDO = transactionRequestDetailsDOBuilder.build(request);
		requestDetailsRepo.save(requestDO);
		return requestDO;

	}

	public TransactionRequestDetailsDO updateStatus(TransactionRequestDetailsDO requestDO,
			Status status) {
		requestDO.setStatus(status);
		requestDetailsRepo.save(requestDO);
		return requestDO;

	}

	public TransactionRequestDetailsDO findDuplicateTransaction(UUID merchantId,
			String merchantOrderID) {
		return requestDetailsRepo.findByMerchantIdAndMerchantOrderIdAndStatusIn(merchantId,
				merchantOrderID, PROCESSED_STATUS);
	}
}
