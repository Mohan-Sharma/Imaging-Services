package com.nzion.superbill.view;

import com.nzion.service.billing.impl.BillingServiceImpl;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.UtilValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 6/13/14
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class SuperBillServlet implements HttpRequestHandler {

    @Autowired
    private CommonCrudService commonCrudService;

    @Autowired
    private BillingServiceImpl billingService;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpServletResponse httpResponse = response;
        String dataCategory = request.getParameter("dataCategory");

        if (UtilValidator.isEmpty(dataCategory) || !doesDataCategoryValid(dataCategory)){
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Data category type is null");
            return;
        }
        try {
            String dataInJson = DataCategory.valueOf(dataCategory).getDataInJson(commonCrudService, billingService);
            response.getOutputStream().print(dataInJson);
            response.getOutputStream().flush();
            response.setContentType("application/json");
            httpResponse.setStatus(HttpServletResponse.SC_OK);
        }
        catch (Exception ex) {
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Invalid data category type");
            ex.printStackTrace();
        }
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    private Boolean doesDataCategoryValid(String dataCategory){
        if (DataCategory.DOCTOR.name().equals(dataCategory)
                || DataCategory.LABTEST.name().equals(dataCategory)
                || DataCategory.REFERRAL.name().equals(dataCategory)
                || DataCategory.GENDER.name().equals(dataCategory))
            return true;
        return false;
    }
}
