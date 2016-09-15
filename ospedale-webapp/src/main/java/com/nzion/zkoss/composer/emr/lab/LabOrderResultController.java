package com.nzion.zkoss.composer.emr.lab;

import com.nzion.domain.File;
import com.nzion.domain.emr.lab.LabRequisition;
import com.nzion.domain.emr.lab.LabResultAttachments;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.zkoss.composer.OspedaleAutowirableComposer;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
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
    private static final String setLabRequisitionWithoutStatus = "UPDATE `lab_requisition` SET `updated_tx_timestamp` = :nowDate WHERE `laborderrequest` = :labOrderRequestId";


    @Init
    public void init(@ExecutionArgParam("labRequisition") LabRequisition labRequisitionId){
        labRequisitionId = commonCrudService.getById(LabRequisition.class,labRequisitionId.getId());
        labResultAttachments = commonCrudService.findByEquality(LabResultAttachments.class, new String[]{"labOrderRequest.id"}, new Object[]{labRequisitionId.getLabOrderRequest().getId()});
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


    public int updateLabRequisitionStatus(final Long labOrderRequestId, boolean check){
        if (!check) {
            UtilMessagesAndPopups.showConfirmation("Number of results uploaded does not match the number of tests. Do you want to mark the order as complete ?", new EventListener() {
                @Override
                public void onEvent(Event evt) throws Exception {
                    if ("onYes".equalsIgnoreCase(evt.getName())) {
                        try{
                            int updateRow;
                            java.sql.Timestamp nowDate = new java.sql.Timestamp(new java.util.Date().getTime());
                            updateRow = namedParameterJdbcTemplate.update(setLabRequisitionStatus, new MapSqlParameterSource("labOrderRequestId",labOrderRequestId).addValue("nowDate",nowDate).addValue("status", LabRequisition.LabRequisitionStatus.COMPLETED.name()));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if ("onNo".equalsIgnoreCase(evt.getName()))
                        try{
                            int updateRow;
                            java.sql.Timestamp nowDate = new java.sql.Timestamp(new java.util.Date().getTime());
                            updateRow = namedParameterJdbcTemplate.update(setLabRequisitionWithoutStatus, new MapSqlParameterSource("labOrderRequestId",labOrderRequestId).addValue("nowDate", nowDate));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                }
            });
        } else {
            try{
                int updateRow1;
                java.sql.Timestamp nowDate = new java.sql.Timestamp(new java.util.Date().getTime());
                updateRow1 = namedParameterJdbcTemplate.update(setLabRequisitionStatus, new MapSqlParameterSource("labOrderRequestId",labOrderRequestId).addValue("nowDate",nowDate).addValue("status", LabRequisition.LabRequisitionStatus.COMPLETED.name()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        return 0;
    }



}
