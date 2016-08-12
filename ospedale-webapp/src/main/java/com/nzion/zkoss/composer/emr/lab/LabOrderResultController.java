package com.nzion.zkoss.composer.emr.lab;

import com.nzion.domain.File;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.domain.emr.lab.LabRequisition;
import com.nzion.domain.emr.lab.LabResultAttachments;
import com.nzion.service.common.CommonCrudService;
import com.nzion.zkoss.composer.OspedaleAutowirableComposer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.zkoss.bind.annotation.*;
import org.zkoss.zul.Filedownload;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Nthdimenzion on 22-Jul-16.
 */
public class LabOrderResultController extends OspedaleAutowirableComposer {
    private CommonCrudService commonCrudService;
    private List<LabResultAttachments> labResultAttachments;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String setLabRequisitionStatus = "UPDATE `lab_requisition` SET `status` =:status, `updated_tx_timestamp` = :nowDate WHERE `laborderrequest` = :labOrderRequestId";


    @Init
    public void init(@ExecutionArgParam("labOrderRequest") LabOrderRequest labOrderRequest){
        labResultAttachments = commonCrudService.findByEquality(LabResultAttachments.class, new String[]{"labOrderRequest.id"}, new Object[]{labOrderRequest.getId()});
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

    @Command("Save")
    @NotifyChange("labResultAttachments")
    public void save(@BindingParam("labResultAttachment") LabResultAttachments labResultAttachment){
        try {
            if(labResultAttachment.getFile() != null) {
                labResultAttachments.add(labResultAttachment);
            }
        }catch (Exception e){}
    }

    @Command("Remove")
    @NotifyChange("labResultAttachments")
    public void remove(@BindingParam(value = "arg1")LabResultAttachments labResultAttachments1){
        try{
            labResultAttachments.remove(labResultAttachments1);
            commonCrudService.delete(labResultAttachments1);
        }catch (Exception e){}

    }


    public int updateLabRequisitionStatus(Long labOrderRequestId){
        int updateRow;
        java.sql.Timestamp nowDate = new java.sql.Timestamp(new java.util.Date().getTime());
        try{
            return updateRow = namedParameterJdbcTemplate.update(setLabRequisitionStatus, new MapSqlParameterSource("labOrderRequestId",labOrderRequestId).addValue("nowDate",nowDate).addValue("status", LabRequisition.LabRequisitionStatus.COMPLETED.name()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }



}
