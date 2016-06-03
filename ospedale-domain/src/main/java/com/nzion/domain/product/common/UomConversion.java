package com.nzion.domain.product.common;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.util.ObjectUtils;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.emr.UnitOfMeasurement;
import com.nzion.util.UtilReflection;

@Table
@Entity
public class UomConversion extends IdGeneratingBaseEntity {
	private static final long serialVersionUID = 1L;

	private UnitOfMeasurement uomFrom;
    private UnitOfMeasurement uomTo;
    private BigDecimal conversionFactor;
    
    @OneToOne
    public UnitOfMeasurement getUomFrom() {
        return uomFrom;
    }

    public void setUomFrom(UnitOfMeasurement uomFrom) {
        this.uomFrom = uomFrom;
    }
    
    @OneToOne
    public UnitOfMeasurement getUomTo() {
        return uomTo;
    }

    public void setUomTo(UnitOfMeasurement uomTo) {
        this.uomTo = uomTo;
    }
    
    public BigDecimal getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(BigDecimal conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public boolean equals(Object o) {
        return UtilReflection.areEqual(this, o, "uomFrom", "uomTo");
    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(new Object[]{uomFrom, uomTo});
    }
}
