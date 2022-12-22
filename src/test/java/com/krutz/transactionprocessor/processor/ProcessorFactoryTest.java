package com.krutz.transactionprocessor.processor;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessorFactoryTest {

	@InjectMocks
	private ProcessorFactory processorFactory;
	@Mock
	private DefaultProcessor defaultProcessor;
	@Mock
	private FailedProcessor failedProcessor;
	@Mock
	private SuccessProcessor successProcessor;

	@Test
	public void whenAmountIs200ShouldReturnSuccessProcessor(){
		TransactionProcessor processor = processorFactory.getProcessor(BigDecimal.valueOf(200));
		Assert.assertEquals(successProcessor,processor);
	}
	@Test
	public void whenAmountIs500ShouldReturnFailedProcessor(){
		TransactionProcessor processor = processorFactory.getProcessor(BigDecimal.valueOf(500));
		Assert.assertEquals(failedProcessor,processor);
	}
	@Test
	public void whenAmountOtherShouldReturnDefaultProcessor(){
		TransactionProcessor processor = processorFactory.getProcessor(BigDecimal.valueOf(100));
		Assert.assertEquals(defaultProcessor,processor);
	}
}
