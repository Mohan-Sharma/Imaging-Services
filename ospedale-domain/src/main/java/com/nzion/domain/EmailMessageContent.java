package com.nzion.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Filter;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.enums.EmailContentType;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "CONTENT_TYPE" }))

public class EmailMessageContent extends IdGeneratingBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmailContentType contentType;
	private String messageBody;

	@Enumerated(EnumType.STRING)
	@Column(name = "CONTENT_TYPE")
	public EmailContentType getContentType() {
	return contentType;
	}

	public void setContentType(EmailContentType contentType) {
	this.contentType = contentType;
	}

	@Column(name = "MESSAGE_BODY", length = 2048)
	public String getMessageBody() {
	return messageBody;
	}

	public void setMessageBody(String messageBody) {
	this.messageBody = messageBody;
	}
}