package com.krutz.transactionprocessor.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantTransactionRequest extends TransactionRequest {

	@NotNull
	private UUID merchantId;
	@NotBlank
	private String merchantOrderId;
	private Map<String, Object> shopperInfo;
	private String signature;
}
