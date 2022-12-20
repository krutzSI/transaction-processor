package com.krutz.transactionprocessor.dao.repository;

import com.krutz.transactionprocessor.dao.model.MerchantDetailsDO;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantDetailsRepo extends JpaRepository<MerchantDetailsDO, UUID> {

	MerchantDetailsDO findByApiKeyAndActive(UUID apiKey, boolean active);

	MerchantDetailsDO findByMerchantId(UUID merchantId);
}

