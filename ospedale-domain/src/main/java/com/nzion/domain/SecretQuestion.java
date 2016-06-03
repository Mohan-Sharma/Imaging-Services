package com.nzion.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(name="SECRET_QUESTION")
public class SecretQuestion extends IdGeneratingBaseEntity {
	
	 /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 9050581606672978231L;
    private String secretQuestion;

    
    @Column(name="SECRET_QUESTION", nullable = true)
	public String getSecretQuestion() {
	return secretQuestion;
	}
	public void setSecretQuestion(String secretQuestion) {
	this.secretQuestion = secretQuestion;
	}
	@Override
	public String toString() {
	return secretQuestion;
	}
	
	
}
