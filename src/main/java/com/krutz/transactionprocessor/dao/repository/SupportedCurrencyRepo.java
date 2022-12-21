package com.krutz.transactionprocessor.dao.repository;

import com.krutz.transactionprocessor.dao.model.SupportedCurrencyDO;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportedCurrencyRepo extends JpaRepository<SupportedCurrencyDO, UUID> {

	List<SupportedCurrencyDO> findByMerchantIdAndActive(UUID merchantId,boolean active);
}
