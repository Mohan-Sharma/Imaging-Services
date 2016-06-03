package com.nzion.superbill.dto;

import com.nzion.domain.Enumeration;
import com.nzion.domain.PostalAddressFields;
import org.zkoss.util.Maps;

import java.sql.Blob;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 6/14/14
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */

public class PatientDto {

    public String image;

    public String personalTitle;

    public String firstName;

    public String middleName;

    public String lastName="";

    public Date dateOfBirth;

    public String age;

    public String gender;

    public String mrnNumber;

    public String mobileNumber="";

    public Long genderId;

    public Integer startIndex;

    public Integer noOfRecordsPerPage;

    public Long patientId;

    public Map<String, Object> postalAddress = new HashMap<String, Object>();


}
