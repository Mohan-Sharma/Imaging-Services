package com.nzion.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Mohan Sharma on 8/1/2015.
 */
public class SimpleCORSFilter implements Filter {

    private static String portalBaseURL;
    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", portalBaseURL);
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Depth, User-Agent, X-File-Size, X-Requested-With, If-Modified-Since, X-File-Name, Cache-Control");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        if (httpServletRequest.getMethod().equals("OPTIONS") ) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        Properties properties = new Properties();
        String profileName = System.getProperty("profile.name") != null ? System.getProperty("profile.name") : "dev";
        try {
            properties.load(SimpleCORSFilter.class.getClassLoader().getResourceAsStream("application-"+profileName+".properties"));
            String portalURL = (String)properties.get("PORTAL_SERVER_URL");
            portalBaseURL = portalURL.replace("/afya-portal", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
