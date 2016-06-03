package com.nzion.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nzion.domain.Roles;
import com.nzion.domain.UserLogin;
import com.nzion.security.passwordcheck.PasswordPolicyErrorStrategy;
import com.nzion.util.Infrastructure;

/**
 * @author Sandeep Prusty
 * Jul 5, 2010
 */
public class PasswordPolicyFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	UserLogin login = Infrastructure.getUserLogin();
	if(login == null || login.hasRole(Roles.SUPER_ADMIN)){
		chain.doFilter(request, response);
		return;
	}
	HttpSession session = ((HttpServletRequest)request).getSession(); 
	PasswordPolicyErrorStrategy errorStrategy = (PasswordPolicyErrorStrategy)session.getAttribute(PasswordPolicyErrorStrategy.HTTP_SESSION_ATTRIBUTE_KEY);
	if(errorStrategy != null){
		if(errorStrategy.hasError())
			errorStrategy.execute((HttpServletRequest)request, (HttpServletResponse)response);
		else 
			session.removeAttribute(PasswordPolicyErrorStrategy.HTTP_SESSION_ATTRIBUTE_KEY);
	}
	chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}