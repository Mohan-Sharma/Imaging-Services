package com.nzion.superbill.view;

import com.google.gson.Gson;
import com.nzion.domain.Roles;
import com.nzion.util.Infrastructure;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 6/26/14
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthorizationServlet implements HttpRequestHandler {

    Gson gson = new Gson();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpServletResponse httpResponse = response;
        Boolean isBiller = false;
        if (Infrastructure.getUserLogin() != null){
            isBiller = Roles.hasRole(Roles.BILLING);
        }
        Map<String,Object> responseMap = new HashMap<String, Object>();
        responseMap.put("isBiller",isBiller);
        response.getOutputStream().print(gson.toJson(responseMap));
        response.getOutputStream().flush();
        httpResponse.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
}
