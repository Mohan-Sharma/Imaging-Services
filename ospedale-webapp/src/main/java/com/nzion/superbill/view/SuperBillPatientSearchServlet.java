package com.nzion.superbill.view;

import com.google.gson.Gson;
import com.nzion.domain.Patient;
import com.nzion.service.common.CommonCrudService;
import com.nzion.superbill.dto.DoctorDto;
import com.nzion.superbill.dto.PatientDto;
import com.nzion.superbill.service.SuperBillService;
import com.nzion.superbill.util.SuperBillUtil;
import com.nzion.util.UtilValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 6/20/14
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class SuperBillPatientSearchServlet implements HttpRequestHandler {

    @Autowired
    private SuperBillService superBillService;

    @Autowired
    private CommonCrudService commonCrudService;

    Gson gson = new Gson();

    private final String searchPatient = "SEARCH";

    private final String addPatient = "ADD";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientAction = request.getParameter("patientaction");
        HttpServletResponse httpResponse = response;
        if (UtilValidator.isEmpty(patientAction) || (!patientAction.equals(addPatient) && !patientAction.equals(searchPatient))) {
            httpResponse.sendError(httpResponse.SC_BAD_REQUEST);
            return;
        }

        BufferedReader requestReader = request.getReader();
        String transformedString = SuperBillUtil.transferServletRequestToString(requestReader);
        PatientDto patientDto = gson.fromJson(transformedString, PatientDto.class);
        try {
            if (patientAction.equals(searchPatient)) {
                List<PatientDto> listOfSearchedPatients = superBillService.searchPatientBy(patientDto);
                response.getOutputStream().print(gson.toJson(listOfSearchedPatients));
                response.getOutputStream().flush();
                httpResponse.setStatus(httpResponse.SC_OK);
            } else {
                if (patientAction.equals(addPatient)) {
                    Long patientId =  superBillService.addPatient(patientDto);
                    Patient patient = commonCrudService.getById(Patient.class,patientId);
                    Map<String,Object> responseMap = new HashMap<String, Object>();
                    responseMap.put("patientId", patientId);
                    responseMap.put("mrnNumber", patient.getAccountNumber());
                    responseMap.put("message", "Patient added successfully");
                    response.getOutputStream().print(gson.toJson(responseMap));
                    response.getOutputStream().flush();
                    httpResponse.setStatus(httpResponse.SC_OK);
                }
            }
            response.setContentType("image/png");
            response.setContentType("application/json");
        } catch (SQLException e) {
            e.printStackTrace();
            httpResponse.sendError(httpResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
