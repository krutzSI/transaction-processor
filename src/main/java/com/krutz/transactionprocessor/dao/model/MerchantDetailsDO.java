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
@Table(name = "merchant_details")
public class MerchantDetailsDO extends AuditDO {

	@Id
	@GeneratedValue
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@Column(name = "merchant_id")
	private UUID merchantId;

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "merchant_email")
	private String merchantEmail;

	@Column(name = "merchant_webhook_endpoint")
	private String merchantWebhookEndpoint;

	@Column(name = "api_key")
	private UUID apiKey;

	@Column(name = "secret_key")
	private String secretKey;

	@Column(name = "active")
	private boolean active;

}
