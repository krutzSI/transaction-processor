package com.krutz.transactionprocessor.service;

import com.krutz.transactionprocessor.dao.model.MerchantDetailsDO;
import com.krutz.transactionprocessor.dao.model.SupportedCurrencyDO;
import com.krutz.transactionprocessor.dao.repository.MerchantDetailsRepo;
import com.krutz.transactionprocessor.dao.repository.SupportedCurrencyRepo;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import liquibase.pro.packaged.S;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MerchantService {

	@Autowired private MerchantDetailsRepo repo;
	@Autowired private SupportedCurrencyRepo supportedCurrencyRepo;

	public MerchantDetailsDO getMerchantByApiKey(UUID apiKey){
		return repo.findByApiKeyAndActive(apiKey,Boolean.TRUE);
	}

	public MerchantDetailsDO getMerchantByMerchantId(UUID merchantId){
		return repo.findByMerchantIdAndActive(merchantId,Boolean.TRUE);
	}

	public List<String> getSupportedCurrencyMerchantId(UUID merchantId){
		List<SupportedCurrencyDO> supportedCurrencyDOS = supportedCurrencyRepo.findByMerchantIdAndActive(
				merchantId, Boolean.TRUE);
		return supportedCurrencyDOS.stream().map(SupportedCurrencyDO::getCurrency)
				.collect(Collectors.toList());
	}
}
