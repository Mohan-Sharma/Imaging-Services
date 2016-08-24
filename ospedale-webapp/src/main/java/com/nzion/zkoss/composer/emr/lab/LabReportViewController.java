package com.nzion.zkoss.composer.emr.lab;

import com.nzion.domain.File;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.domain.emr.lab.LabResultAttachments;
import com.nzion.service.common.CommonCrudService;
import com.nzion.zkoss.composer.OspedaleAutowirableComposer;
import org.zkoss.bind.annotation.*;
import org.zkoss.zul.Filedownload;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Nthdimenzion on 23-Aug-16.
 */
public class LabReportViewController extends OspedaleAutowirableComposer {
    private CommonCrudService commonCrudService;
    private List<LabResultAttachments> labResultAttachments;

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

}
