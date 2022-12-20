package com.krutz.transactionprocessor.dto.response;

import com.krutz.transactionprocessor.constant.Status;
import java.util.UUID;
import lombok.Data;

@Data
public class BaseResponse {
	protected UUID transactionId;
	protected UUID merchantId;
	protected String merchantOrderId;
	protected Status status;
}
