package com.krutz.transactionprocessor.constant;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


public class StatusTest {

	@Test
	public void getDescription(){
		Assert.assertEquals("Accepted",Status.ACCEPTED.getDescription());
	}
}
