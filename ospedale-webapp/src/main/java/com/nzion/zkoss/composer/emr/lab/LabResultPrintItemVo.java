package com.nzion.zkoss.composer.emr.lab;

import com.nzion.domain.emr.lab.OBXSegment;

import java.util.List;

/**
 * @author: NthDimenzion
 * @since 1.0
 */
public class LabResultPrintItemVo {

    private String panelName;

    private String remarks;

    private List<OBXSegment> obxSegments;

    public LabResultPrintItemVo() {
    }

    public LabResultPrintItemVo(String panelName, String remarks, List<OBXSegment> obxSegments) {
        this.panelName = panelName;
        this.remarks = remarks;
        this.obxSegments = obxSegments;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<OBXSegment> getObxSegments() {
        return obxSegments;
    }

    public void setObxSegments(List<OBXSegment> obxSegments) {
        this.obxSegments = obxSegments;
    }
}
