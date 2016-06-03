package com.nzion.security.passwordcheck;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Required;

import com.nzion.service.PasswordPolicyService;
import com.nzion.util.Infrastructure;

/**
 * @author Sandeep Prusty
 * Jul 1, 2010
 */
public class ExpiredPasswordStrategy implements PasswordPolicyErrorStrategy{
	private PasswordPolicyService passwordPolicyService;
	
	public boolean hasError() {
	return passwordPolicyService.checkPasswordExpiration(Infrastructure.getUserLogin());
	}

	public void execute(HttpServletRequest request, HttpServletResponse response) {
	try {
		request.getRequestDispatcher("/changepassword.zul").forward(request, response);
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
	}
	
	@Resource
	@Required
	public void setPasswordPolicyService(PasswordPolicyService passwordPolicyService) {
	this.passwordPolicyService = passwordPolicyService;
	}
}