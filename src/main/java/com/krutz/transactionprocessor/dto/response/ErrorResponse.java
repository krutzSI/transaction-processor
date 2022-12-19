package com.krutz.transactionprocessor.dto.response;

import com.krutz.transactionprocessor.constant.Status;
import java.util.UUID;
import lombok.Data;

@Data
public class ErrorResponse extends BaseResponse{
	private String errorCode;
	private String errorDescription;
	private String remarks;
}
