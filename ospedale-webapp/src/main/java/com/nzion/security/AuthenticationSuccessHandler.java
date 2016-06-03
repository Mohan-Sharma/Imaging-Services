package com.nzion.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;	

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.nzion.domain.Location;
import com.nzion.domain.PasswordPolicy;
import com.nzion.domain.Roles;
import com.nzion.domain.UserLogin;
import com.nzion.enums.EventType;
import com.nzion.security.passwordcheck.PasswordPolicyErrorStrategy;
import com.nzion.service.PasswordPolicyService;
import com.nzion.service.UserLoginService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.common.impl.ApplicationEvents;
import com.nzion.util.Infrastructure;
import com.nzion.util.UserLoginUserDetailsAdapter;
import com.nzion.util.UtilValidator;


public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements LogoutSuccessHandler{

	private List<PasswordPolicyErrorStrategy> passwordCheckStrategies;
	
	private UserLoginService userLoginService;
	private CommonCrudService commonCrudService;
	private PasswordPolicyService passwordPolicyService;
	@Value("${LOGOUT_SERVER_URL}")
	private String LOGOUT_SERVER_URL;

	public PasswordPolicyService getPasswordPolicyService() {
	return passwordPolicyService;
	}
	
	@Resource(name="passwordPolicyService")
	public void setPasswordPolicyService(PasswordPolicyService passwordPolicyService) {
	this.passwordPolicyService = passwordPolicyService;
	}

	
	public void setUserLoginService(UserLoginService userLoginService) {
	this.userLoginService = userLoginService;
	}

	public void setPasswordCheckStrategies(List<PasswordPolicyErrorStrategy> passwordCheckStrategies) {
	this.passwordCheckStrategies = passwordCheckStrategies;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
	UserLogin login = Infrastructure.getUserLogin();
	executePasswordStrategies(request, response, authentication, login);
	if(login.getPerson() != null && UtilValidator.isNotEmpty(login.getPerson().getLocations())){
		ArrayList<Location> locs = new ArrayList<Location>(login.getPerson().getLocations());
		Collections.sort(locs);
		Infrastructure.setSelectedLocation(locs.iterator().next());
		request.getSession().setAttribute("_location", Infrastructure.getSelectedLocation());		
	}
	
//	if(login.getPractice() != null && login.getPractice().getTimeZone() != null)
//		request.getSession().setAttribute(Attributes.PREFERRED_TIME_ZONE, login.getPractice().getTimeZone());
	request.getSession().setAttribute("_role", login.getAuthorization().getMostPriorRole());
	userLoginService.resetFailedLoginCount(login);
	ApplicationEvents.postEvent(EventType.APPLICATION_LOGIN,null,login,"User logged in.");
    request.getSession().setMaxInactiveInterval(-1);
	PasswordPolicy policy = passwordPolicyService.getEffectivePasswordPolicy();
	int timeout = policy.getSessionTimeOut();
	if(timeout == 0)timeout = -1;
	request.getSession().setMaxInactiveInterval(timeout);
	super.onAuthenticationSuccess(request, response, authentication);
	}

	private void executePasswordStrategies(HttpServletRequest request, HttpServletResponse response, Authentication authentication, UserLogin login) {
	if(UtilValidator.isEmpty(passwordCheckStrategies) || login.hasRole(Roles.SUPER_ADMIN)) 
		return;
	for(PasswordPolicyErrorStrategy strategy : passwordCheckStrategies)
		if(strategy.hasError()){
			request.getSession().setAttribute(PasswordPolicyErrorStrategy.HTTP_SESSION_ATTRIBUTE_KEY, strategy);
			return;
		}
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
	if(authentication != null){
		UserLogin login = ((UserLoginUserDetailsAdapter) authentication.getPrincipal()).getUserLogin();
		ApplicationEvents.postEvent(EventType.APPLICATION_LOGOUT,null,login, "User logged out.");
	}
	//response.sendRedirect(request.getContextPath()+"/login.zhtml");
		/*Cookie[] cookies = request.getCookies();
		for(Cookie cookie :  cookies){
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}*/
		if(UtilValidator.isNotEmpty(LOGOUT_SERVER_URL)){
			response.sendRedirect(LOGOUT_SERVER_URL);
		}else
			response.sendRedirect(request.getContextPath()+"/login.zhtml");
	}

	public void setCommonCrudService(CommonCrudService commonCrudService) {
	this.commonCrudService = commonCrudService;
	}

	public CommonCrudService getCommonCrudService() {
	return commonCrudService;
	}
}