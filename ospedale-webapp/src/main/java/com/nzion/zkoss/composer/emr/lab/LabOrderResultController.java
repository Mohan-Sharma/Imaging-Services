package com.nzion.zkoss.composer.emr.lab;

import com.nzion.domain.File;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.domain.emr.lab.LabRequisition;
import com.nzion.domain.emr.lab.LabResultAttachments;
import com.nzion.domain.emr.lab.LabTest;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.query.CommonFinder;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDateTime;
import com.nzion.zkoss.composer.OspedaleAutowirableComposer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Filedownload;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Nthdimenzion on 22-Jul-16.
 */
public class LabOrderResultController extends OspedaleAutowirableComposer {
    private CommonCrudService commonCrudService;
    private List<LabResultAttachments> labResultAttachments;

    /*public CommonFinder getCommonFinder() {
        return commonFinder;
    }

    public void setCommonFinder(CommonFinder commonFinder) {
        this.commonFinder = commonFinder;
    }*/
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

  //  private CommonFinder commonFinder;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String setLabRequisitionStatus = "UPDATE `lab_requisition` SET `status` =:status, `updated_tx_timestamp` = :nowDate WHERE `laborderrequest` = :labOrderRequestId";


    @Init
    public void init(@ExecutionArgParam("labOrderRequest") LabOrderRequest labOrderRequest){
        labResultAttachments = commonCrudService.findByEquality(LabResultAttachments.class, new String[]{"labOrderRequest.id"}, new Object[]{labOrderRequest.getId()});
      //  commonFinder = Infrastructure.getSpringBean("commonFinder");
    }

    public CommonCrudService getCommonCrudService() {
        return commonCrudService;
    }

    public void setCommonCrudService(CommonCrudService commonCrudService) {
        this.commonCrudService = commonCrudService;
    }

    public List<LabResultAttachments> getLabResultAttachments() {
        return labResultAttachments;
    }

    public void setLabResultAttachments(List<LabResultAttachments> labResultAttachments) {
        this.labResultAttachments = labResultAttachments;
    }


    @Command("downloadAttachment")
    public void downloadAttachment(@BindingParam("fileObject") LabResultAttachments each) throws FileNotFoundException {
        File file = each.getFile();
        InputStream in = new FileInputStream(file.getFilePath());
        Filedownload.save(in, file.getFileType(), file.getFileName());
    }


    public int updateLabRequisitionStatus(Long labOrderRequestId){
        int updateRow;
        //java.sql.Date nowDate = new java.sql.Date(UtilDateTime.nowDate().getTime());
        java.sql.Timestamp nowDate = new java.sql.Timestamp(new java.util.Date().getTime());
        try{
            return updateRow = namedParameterJdbcTemplate.update(setLabRequisitionStatus, new MapSqlParameterSource("labOrderRequestId",labOrderRequestId).addValue("nowDate",nowDate).addValue("status", LabRequisition.LabRequisitionStatus.COMPLETED.name()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
