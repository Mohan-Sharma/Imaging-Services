package com.nzion.service;

import com.nzion.domain.File;
import com.nzion.hibernate.ext.multitenant.TenantIdHolder;
import com.nzion.service.common.CommonCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class DownloadAttachmentServlet extends HttpServlet{

    @Autowired
    CommonCrudService commonCrudService;

    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext()); }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tenantId = request.getParameter("tenantId");
        //String obxId = request.getParameter("obxId");
        String fileId = request.getParameter("fileId");
        if(tenantId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "tenant cannot be null");
            return;
        }
        if(fileId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "fileId cannot be null");
            return;
        }
        TenantIdHolder.setTenantId(tenantId);
        PrintWriter out = response.getWriter();
        try {
            //OBXSegment obxSegment = commonCrudService.getById(OBXSegment.class, Long.parseLong(obxId));
            //File file = obxSegment.getFile();
            File file = commonCrudService.getById(File.class, Long.parseLong(fileId));
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");
            FileInputStream fileInputStream = new FileInputStream(file.getFilePath());

            int i;
            while ((i = fileInputStream.read()) != -1) {
                out.write(i);
            }
            fileInputStream.close();
            out.print("success");
        } catch (Exception e){
            out.print("failed");
            e.printStackTrace();
        }
        out.close();
    }
    public static void main(String args[]){
        //System.out.println( UtilDateTime.addHrsToDate(new Date(), 720) );
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

}
