package com.nzion.security;

/**
 * Created by Asus on 7/10/2015.
 */

import com.nzion.domain.Location;
import com.nzion.domain.UserLogin;
import com.nzion.enums.EventType;
import com.nzion.service.common.impl.ApplicationEvents;
import com.nzion.util.Infrastructure;
import com.nzion.util.UserLoginUserDetailsAdapter;
import com.nzion.util.UtilValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Asus on 7/10/2015.
 */
public class CustomTokenBasedRememberMeService extends TokenBasedRememberMeServices {

    static String LOGOUT_SERVER_URL = "";

    /*static {
        Properties properties = new Properties();
        try {
            String profileName = System.getProperty("profile.name") != null ? System.getProperty("profile.name") : "dev";
            properties.load(CustomTokenBasedRememberMeService.class.getClassLoader().getResourceAsStream("application-"+profileName+".properties"));
            LOGOUT_SERVER_URL = (String) properties.get("LOGOUT_SERVER_URL");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public CustomTokenBasedRememberMeService() {
        super();
    }

    private final String HEADER_SECURITY_TOKEN = "token";

    @Override
    protected String makeTokenSignature(long tokenExpiryTime, String username, String password) {
        return username;
    }

    protected boolean isTokenExpired(long tokenExpiryTime) {
        return false;
    }

    String getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();


        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        String[] paramValues = request.getParameterValues("token");
        if (paramValues != null) {
            return paramValues[0];
        }

        String token = (String) request.getSession().getAttribute("token");
        return token;
    }

    @Override
    protected String extractRememberMeCookie(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        String token = getCookie(request, "token");

        if ((token == null) || (token.length() == 0)) {
            return null;
        }
        request.getSession().setAttribute("token", token);

        return token;
    }
    @Override
    protected Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails user) {
        UserLogin login = ((UserLoginUserDetailsAdapter)user).getUserLogin();
        if (login.getPerson() != null && UtilValidator.isNotEmpty(login.getPerson().getLocations())) {
            ArrayList<Location> locs = new ArrayList<Location>(login.getPerson().getLocations());
            Collections.sort(locs);
            Infrastructure.setSelectedLocation(locs.iterator().next());
            request.getSession().setAttribute("_location", Infrastructure.getSelectedLocation());
        }

//	if(login.getPractice() != null && login.getPractice().getTimeZone() != null)
//		request.getSession().setAttribute(Attributes.PREFERRED_TIME_ZONE, login.getPractice().getTimeZone());
        /*if(UtilValidator.isNotEmpty(login.getSelectedDefaultRole())){
            request.getSession().setAttribute("_default_role", login.getSelectedDefaultRole());
        }*/
        request.getSession().setAttribute("_role", login.getAuthorization().getMostPriorRole());
        //request.getSession().setAttribute("_role", login.getAuthorization().getMostPriorRole());
        // userLoginService.resetFailedLoginCount(login);
        ApplicationEvents.postEvent(EventType.APPLICATION_LOGIN, null, login, "User logged in.");
        request.getSession().setMaxInactiveInterval(-1);
        //PasswordPolicy policy = passwordPolicyService.getEffectivePasswordPolicy();
        //int timeout = policy.getSessionTimeOut();
        //if(timeout == 0)timeout = -1;
        //request.getSession().setMaxInactiveInterval(timeout);
        return super.createSuccessfulAuthentication(request,user);
    }

    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        UserLogin login = Infrastructure.getUserLogin();
        //getUserDetailsService().executePasswordStrategies(request, response, authentication, login);
        if (login.getPerson() != null && UtilValidator.isNotEmpty(login.getPerson().getLocations())) {
            ArrayList<Location> locs = new ArrayList<Location>(login.getPerson().getLocations());
            Collections.sort(locs);
            Infrastructure.setSelectedLocation(locs.iterator().next());
            request.getSession().setAttribute("_location", Infrastructure.getSelectedLocation());
        }

//	if(login.getPractice() != null && login.getPractice().getTimeZone() != null)
//		request.getSession().setAttribute(Attributes.PREFERRED_TIME_ZONE, login.getPractice().getTimeZone());
        /*if(UtilValidator.isNotEmpty(login.getSelectedDefaultRole())){
            request.getSession().setAttribute("_default_role", login.getSelectedDefaultRole());
        }*/
        request.getSession().setAttribute("_role", login.getAuthorization().getMostPriorRole());
        //request.getSession().setAttribute("_role", login.getAuthorization().getMostPriorRole());
        // userLoginService.resetFailedLoginCount(login);
        ApplicationEvents.postEvent(EventType.APPLICATION_LOGIN, null, login, "User logged in.");
        request.getSession().setMaxInactiveInterval(-1);
        //PasswordPolicy policy = passwordPolicyService.getEffectivePasswordPolicy();
        //int timeout = policy.getSessionTimeOut();
        //if(timeout == 0)timeout = -1;
        //request.getSession().setMaxInactiveInterval(timeout);
        super.onLoginSuccess(request, response, successfulAuthentication);
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0?contextPath:"/";
    }
    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0);
        cookie.setPath(this.getCookiePath(request));
        response.addCookie(cookie);
    }
 /*commented by Mohan Sharma to hit spring's default logout api*/

   /* public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)  {
        if(authentication != null){
            UserLogin login = ((UserLoginUserDetailsAdapter) authentication.getPrincipal()).getUserLogin();
            ApplicationEvents.postEvent(EventType.APPLICATION_LOGOUT,null,login, "User logged out.");
        }
        if (UtilValidator.isNotEmpty(LOGOUT_SERVER_URL)) {
            try {
                response.sendRedirect(LOGOUT_SERVER_URL);
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendRedirect(request.getContextPath() + "/login.zhtml");
            }  catch(Exception e) {
                e.printStackTrace();
            }
        }
    }*/
}