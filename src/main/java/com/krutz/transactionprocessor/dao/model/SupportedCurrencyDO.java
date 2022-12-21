package com.krutz.transactionprocessor.dao.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SUPPORTED_CURRENCY")
public class SupportedCurrencyDO extends AuditDO{
	@Id
	@GeneratedValue
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@Column(name = "merchant_id")
	private UUID merchantId;

	@Column(name = "currency")
	private String currency;

	@Column(name = "active")
	private boolean active;
}
