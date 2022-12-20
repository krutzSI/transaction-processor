package com.krutz.transactionprocessor.dao.model;

import com.krutz.transactionprocessor.constant.Status;
import com.krutz.transactionprocessor.dao.converter.MapToJSONConverter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Generated;

@Data
@Entity
@Table(name = "transaction_request_details")
public class TransactionRequestDetailsDO extends AuditDO {

	@Id
	@GeneratedValue
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@Column(name = "transaction_id")
	private UUID transactionId;

	@Column(name = "merchant_id")
	private UUID merchantId;

	@Column(name = "merchant_order_id")
	private String merchantOrderId;

	@Column(name = "amount")
	private BigDecimal transactionAmount;

	@Column(name = "payment_method")
	private String paymentMethod;

	@Column(name = "transaction_date")
	private LocalDateTime transactionDate;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(name = "shopper_info")
	@Convert(converter = MapToJSONConverter.class)
	private Map<String, Object> shopperInfo;

}
