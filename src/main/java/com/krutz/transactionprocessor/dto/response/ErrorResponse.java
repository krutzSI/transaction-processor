package com.krutz.transactionprocessor.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {
	private String errorCode;
	private String errorDescription;
	private String error_message;
}
