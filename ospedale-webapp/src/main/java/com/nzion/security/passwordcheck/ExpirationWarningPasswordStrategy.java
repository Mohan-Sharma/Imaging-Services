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
public class ExpirationWarningPasswordStrategy implements PasswordPolicyErrorStrategy{
	
	private PasswordPolicyService passwordPolicyService;

	public boolean hasError() {
	return passwordPolicyService.checkWarningPeriodArrival(Infrastructure.getUserLogin());
	}

	public void execute(HttpServletRequest request, HttpServletResponse response) {
	request.getSession().setAttribute("passwordExpirationWarningMessage","Password Will Expire in less than 2 days");
	}
	
	@Resource
	@Required
	public void setPasswordPolicyService(PasswordPolicyService passwordPolicyService) {
	this.passwordPolicyService = passwordPolicyService;
	}
}