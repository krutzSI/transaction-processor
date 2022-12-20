package com.krutz.transactionprocessor.dao.repository;

import com.krutz.transactionprocessor.constant.Status;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRequestDetailsRepo extends
		JpaRepository<TransactionRequestDetailsDO, UUID> {

	TransactionRequestDetailsDO findByMerchantIdAndMerchantOrderIdAndStatusIn(UUID merchantId,
			String merchantOrderId, List<Status> statusList);

	TransactionRequestDetailsDO findByTransactionId(UUID transactionId);


	List<TransactionRequestDetailsDO> findByMerchantIdAndTransactionDateBetween(UUID transactionId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
