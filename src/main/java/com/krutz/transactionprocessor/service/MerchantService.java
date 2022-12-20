package com.krutz.transactionprocessor.service;

import com.krutz.transactionprocessor.dao.model.MerchantDetailsDO;
import com.krutz.transactionprocessor.dao.repository.MerchantDetailsRepo;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MerchantService {

	@Autowired private MerchantDetailsRepo repo;

	public MerchantDetailsDO getMerchantByApiKey(UUID apiKey){
		return repo.findByApiKeyAndActive(apiKey,Boolean.TRUE);
	}

}
