package com.krutz.transactionprocessor.dto.response;

import com.krutz.transactionprocessor.constant.Status;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StatusUpdateWebhookResponse {

	private UUID merchantId;
	private String merchantOrderId;
	private UUID transactionId;
	private Status status;
	private BigDecimal amount;
	private String currency;
	private String signature;
}
