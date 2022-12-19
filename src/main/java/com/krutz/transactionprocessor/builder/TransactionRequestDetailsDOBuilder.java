package com.krutz.transactionprocessor.builder;

import com.krutz.transactionprocessor.constant.Status;
import com.krutz.transactionprocessor.dao.model.TransactionRequestDetailsDO;
import com.krutz.transactionprocessor.dto.request.MerchantTransactionRequest;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionRequestDetailsDOBuilder {

	public TransactionRequestDetailsDO build(MerchantTransactionRequest transactionRequest){
		TransactionRequestDetailsDO transactionRequestDetailsDO=new TransactionRequestDetailsDO();
		transactionRequestDetailsDO.setTransactionReference(UUID.randomUUID());
		transactionRequestDetailsDO.setStatus(Status.ACCEPTED);
		BeanUtils.copyProperties(transactionRequest, transactionRequestDetailsDO);
		return transactionRequestDetailsDO;
	}

}
