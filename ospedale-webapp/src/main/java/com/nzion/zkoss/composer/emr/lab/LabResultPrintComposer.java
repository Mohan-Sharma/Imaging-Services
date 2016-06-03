package com.nzion.zkoss.composer.emr.lab;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;

import com.lowagie.text.pdf.codec.Base64.InputStream;
import com.nzion.domain.emr.lab.OBRSegment;
import com.nzion.domain.emr.lab.OBXSegment;
import com.nzion.repository.common.CommonCrudRepository;
import com.nzion.util.UtilValidator;
import com.nzion.zkoss.composer.OspedaleAutowirableComposer;

public class LabResultPrintComposer extends OspedaleAutowirableComposer {

    private int pageRecordLimit;

    @Autowired
    private CommonCrudRepository commonCrudRepository;

    private List<LabResultPrintVO> labResults;

    public LabResultPrintComposer() {
        BufferedInputStream is = (BufferedInputStream) LabResultPrintComposer.class.getClassLoader().getResourceAsStream("labResultPrint.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pageRecordLimit = Integer.valueOf(properties.getProperty("noOfRecords"));
    }

    @Override
    public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
        Long obrId = new Long(Executions.getCurrent().getParameterMap().get("obrId")[0]);
        labResults = new ArrayList<LabResultPrintVO>();
        List<OBRSegment> obrSegmentList = commonCrudRepository.findByEquality(com.nzion.domain.emr.lab.OBRSegment.class, new String[]{"requisitionNumber"}, new Object[]{String.valueOf(obrId)});
        buildLabResultDetail(obrSegmentList);
        return super.doBeforeCompose(page, parent, compInfo);
    }

    private void buildLabResultDetail(List<OBRSegment> obrSegmentList) {
        List<LabResultPrintItemVo> labResultList = new ArrayList<LabResultPrintItemVo>();
        int totalNoOfRecord = 0;
        for (OBRSegment obrSegment : obrSegmentList) {
            List<OBXSegment> observations = new ArrayList<OBXSegment>(obrSegment.getObservations());
            Collections.sort(observations);
            if ((totalNoOfRecord + observations.size()) > pageRecordLimit) {
                //LabResultPrintItemVo labResultPrintItemVo = new LabResultPrintItemVo(obrSegment.getLabTestPanel().getPanelName(), obrSegment.getTechnicianComment(), observations);
            	LabResultPrintItemVo labResultPrintItemVo = new LabResultPrintItemVo(obrSegment.getLabTest().getTestDescription(), obrSegment.getTechnicianComment(), observations);
            	List<LabResultPrintItemVo> currentLabTestList = new ArrayList<LabResultPrintItemVo>();
                currentLabTestList.add(labResultPrintItemVo);
                if (UtilValidator.isNotEmpty(labResultList)) {
                    buildLabResultPrintVo(true, new ArrayList<LabResultPrintItemVo>(labResultList), obrSegment.getTechnicianComment());
                    labResultList.clear();
                }
                int count = 0;
                for (LabResultPrintItemVo labResultPrintItemVo1 : currentLabTestList) {
                    count = count + labResultPrintItemVo1.getObxSegments().size();
                }
                totalNoOfRecord = count;
                labResultList = currentLabTestList;
            } else {
                totalNoOfRecord = totalNoOfRecord + observations.size();
                //LabResultPrintItemVo labResultPrintItemVo = new LabResultPrintItemVo(obrSegment.getLabTestPanel().getPanelName(), obrSegment.getTechnicianComment(), observations);
                LabResultPrintItemVo labResultPrintItemVo = new LabResultPrintItemVo(obrSegment.getLabTest().getTestDescription(), obrSegment.getTechnicianComment(), observations);
                labResultList.add(labResultPrintItemVo);
            }

        }
        if (UtilValidator.isNotEmpty(labResultList)) {
            buildLabResultPrintVo(true, labResultList, "");
        }
    }

    private void buildLabResultPrintVo(boolean pageBreakRequire, List<LabResultPrintItemVo> labResultList, String remarks) {
        LabResultPrintVO previousLabResultPrintVO = new LabResultPrintVO(true, labResultList);
        labResults.add(previousLabResultPrintVO);
    }

    public List<LabResultPrintVO> getLabResults() {
        return labResults;
    }


}
