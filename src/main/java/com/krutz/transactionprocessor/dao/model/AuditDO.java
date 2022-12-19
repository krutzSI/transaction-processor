package com.krutz.transactionprocessor.dao.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@Data
public class AuditDO {

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@CreationTimestamp
	@Column(name = "created_on")
	private LocalDateTime createdDateTime;

	@UpdateTimestamp
	@Column(name = "updated_on")
	private LocalDateTime updatedOn;
}
