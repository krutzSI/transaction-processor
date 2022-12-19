package com.krutz.transactionprocessor.processor;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessorFactory {

	final private long SUCCESS_AMOUNT = 200L;
	final private long FAILED_AMOUNT = 500L;
	@Autowired
	private DefaultProcessor defaultProcessor;
	@Autowired
	private FailedProcessor failedProcessor;
	@Autowired
	private SuccessProcessor successProcessor;


	public TransactionProcessor getProcessor(BigDecimal requestAmount) {

		if (requestAmount.compareTo(BigDecimal.valueOf(SUCCESS_AMOUNT)) == 0) {
			return successProcessor;
		} else if (requestAmount.compareTo(BigDecimal.valueOf(FAILED_AMOUNT)) == 0) {
			return failedProcessor;
		}
		return defaultProcessor;
	}
}
