package com.krutz.transactionprocessor.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TransactionResponse extends BaseResponse{

	private BigDecimal amount;
	private LocalDateTime date;
}
