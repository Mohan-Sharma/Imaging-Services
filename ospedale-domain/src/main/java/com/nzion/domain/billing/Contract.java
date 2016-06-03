package com.nzion.domain.billing;

import com.nzion.domain.HistoricalModel;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.pms.InsuranceProvider;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;
import org.hibernate.annotations.Filter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)")
public class Contract extends IdGeneratingBaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String name;

    private HistoricalModel effectivePeriod = new HistoricalModel();

    private CONTRACTTYPE contractType = CONTRACTTYPE.SELF;

    private Set<InsuranceProvider> insuranceProviders = new HashSet<InsuranceProvider>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Embedded
    public HistoricalModel getEffectivePeriod() {
        effectivePeriod = (effectivePeriod == null ? new HistoricalModel() : effectivePeriod);
        return effectivePeriod;
    }

    public void setEffectivePeriod(HistoricalModel effectivePeriod) {
        this.effectivePeriod = effectivePeriod;
    }

    @Transient
    public boolean isCurrentlyEffective() {
        if (effectivePeriod != null)
            return !effectivePeriod.isExpired(UtilDateTime.nowDate());
        else
            return false;
    }

    @Transient
    public boolean isEffectiveForDate(Date date) {
        if (effectivePeriod != null)
            return !effectivePeriod.isExpired(date);
        else
            return false;
    }

    public static Contract findCurrentEffectiveContract(Collection<Contract> contracts) {
        if (UtilValidator.isNotEmpty(contracts)) {
            for (Contract contract : contracts) {
                if (contract.isCurrentlyEffective()) {
                    return contract;
                }
            }
        }
        return null;
    }

    public static Contract findEffectiveContractForDate(Collection<Contract> contracts, Date date) {
        if (UtilValidator.isNotEmpty(contracts)) {
            for (Contract contract : contracts) {
                if (contract.isEffectiveForDate(date)) {
                    return contract;
                }
            }
        }
        return null;
    }

    @Enumerated(EnumType.STRING)
    public CONTRACTTYPE getContractType() {
        return contractType;
    }

    public void setContractType(CONTRACTTYPE contractType) {
        this.contractType = contractType;
    }

    public static enum CONTRACTTYPE {
        SELF, INSURANCE, CORPORATE;
    }

    @ManyToMany(targetEntity = InsuranceProvider.class, fetch = FetchType.EAGER)
    public Set<InsuranceProvider> getInsuranceProviders() {
        return insuranceProviders;
    }

    public void setInsuranceProviders(Set<InsuranceProvider> insuranceProviders) {
        this.insuranceProviders = insuranceProviders;
    }

    public void addInsuranceProvider(InsuranceProvider insuranceProvider) {
        insuranceProviders = insuranceProviders == null ? new HashSet<InsuranceProvider>() : insuranceProviders;
        insuranceProviders.add(insuranceProvider);
    }

}
