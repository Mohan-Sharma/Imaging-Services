package com.nzion.security.passwordcheck;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sandeep Prusty
 * Jul 1, 2010
 */
public interface PasswordPolicyErrorStrategy {
	
	String HTTP_SESSION_ATTRIBUTE_KEY = "PASSWORD_POLICY_HTTP_SESSION_ATTRIBUTE_KEY";

	boolean hasError();
	
	void execute(HttpServletRequest request, HttpServletResponse response);
}