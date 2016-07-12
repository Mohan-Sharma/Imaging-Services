package com.nzion.zkoss.composer;

import com.nzion.domain.Practice;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.PortalRestServiceConsumer;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nthdimenzion on 11-Jul-16.
 */
@VariableResolver(DelegatingVariableResolver.class)
public class ReferralRequestController {
    @WireVariable
    private CommonCrudService commonCrudService;
    private List referralLists = new ArrayList();

    @Init
    public void init(){
        com.nzion.domain.Practice practice = commonCrudService.getAll(Practice.class) != null ? commonCrudService.getAll(Practice.class).get(0) : null ;
        referralLists = PortalRestServiceConsumer.fetchReferralOrder(practice.getTenantId());
    }

    public List getReferralLists() {
        return referralLists;
    }

    public void setReferralLists(List referralLists) {
        this.referralLists = referralLists;
    }


    public CommonCrudService getCommonCrudService() {
        return commonCrudService;
    }

    public void setCommonCrudService(CommonCrudService commonCrudService) {
        this.commonCrudService = commonCrudService;
    }
}
