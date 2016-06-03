package com.nzion.repository;

import com.nzion.domain.PasswordPolicy;
import com.nzion.domain.Practice;

public interface PasswordPolicyRepository extends BaseRepository{
	
	PasswordPolicy getPasswordPolicy();
	
	PasswordPolicy getDefaultPasswordPolicy();
}
