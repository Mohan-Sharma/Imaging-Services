package com.nzion.zkoss.composer.emr.lab;

import java.util.List;
import java.util.Map;

public class LabResultPrintVO {

    private boolean pageBreakRequire;

    private List<LabResultPrintItemVo> labResults;

    public LabResultPrintVO(boolean pageBreakRequire, List<LabResultPrintItemVo> labResults) {
        this.pageBreakRequire = pageBreakRequire;
        this.labResults = labResults;
    }

    public boolean isPageBreakRequire() {
        return pageBreakRequire;
    }

    public void setPageBreakRequire(boolean pageBreakRequire) {
        this.pageBreakRequire = pageBreakRequire;
    }

    public List<LabResultPrintItemVo> getLabResults() {
        return labResults;
    }

}
