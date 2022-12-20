package com.krutz.transactionprocessor.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Data;

@Data
public class TransactionDetailsResponse extends TransactionResponse{
	private Map<String,Object> shopperInfo;
}
