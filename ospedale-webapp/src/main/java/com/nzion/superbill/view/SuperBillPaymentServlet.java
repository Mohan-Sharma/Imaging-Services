package com.nzion.superbill.view;

import com.google.gson.Gson;
import com.nzion.superbill.command.InvoicePaymentCommand;
import com.nzion.superbill.service.SuperBillService;
import com.nzion.superbill.util.SuperBillUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 6/25/14
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class SuperBillPaymentServlet implements HttpRequestHandler {

    @Autowired
    private SuperBillService superBillService;

    Gson gson = new Gson();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpServletResponse httpResponse = response;
        try{
            BufferedReader requestReader = request.getReader();
            String transformedString = SuperBillUtil.transferServletRequestToString(requestReader);
            InvoicePaymentCommand invoicePaymentCommand = gson.fromJson(transformedString, InvoicePaymentCommand.class);
            Long invoiceId =  superBillService.generateInvoice(invoicePaymentCommand);

            Map<String,Object> responseMap = new HashMap<String, Object>();
            responseMap.put("message","The invoice generated successfully");
            responseMap.put("invoiceId",invoiceId);
            response.getOutputStream().print(gson.toJson(responseMap));
            response.getOutputStream().flush();
            response.setContentType("application/json");
            httpResponse.setStatus(HttpServletResponse.SC_OK);
        }
        catch (Exception ex){
            ex.printStackTrace();
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Error occurred while generating the Invoice id");
        }
    }


    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
}
