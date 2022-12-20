package com.krutz.transactionprocessor.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class TransactionErrorResponse extends BaseResponse {

	private String errorCode;
	private String errorDescription;
	private String remarks;
}
