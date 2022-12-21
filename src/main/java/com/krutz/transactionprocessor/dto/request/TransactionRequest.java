package com.krutz.transactionprocessor.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class TransactionRequest {
	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
	protected BigDecimal transactionAmount;
	@NotBlank
	@Pattern(regexp = "^[A-Za-z]{3}$", message = "should be 3 DIGIT CURRENCY ISO CODE")
	protected String currency;
	@NotBlank
	protected String paymentMethod;
	@NotNull
	protected LocalDateTime transactionDate;
}
