package com.nzion.domain.screen;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Filter;

import com.nzion.domain.Salutable;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.util.Constants;
import com.nzion.util.UtilReflection;
import com.nzion.util.UtilValidator;

/**
 * @author Sandeep Prusty
 * Aug 1, 2011
 */

@Entity
@Table(name = "NAMING_DISPLAY_CONFIG")
public class NamingDisplayConfig extends IdGeneratingBaseEntity {
	
	private String position1;
	
	private String position2;
	
	private String position3;
	
	private String position4;
	
	private String position5;
	
	private String position6;
	
	public String getPosition1() {
	return position1;
	}

	public void setPosition1(String position1) {
	this.position1 = position1;
	}

	public String getPosition2() {
	return position2;
	}

	public void setPosition2(String position2) {
	this.position2 = position2;
	}

	public String getPosition3() {
	return position3;
	}

	public void setPosition3(String position3) {
	this.position3 = position3;
	}

	public String getPosition4() {
	return position4;
	}

	public void setPosition4(String position4) {
	this.position4 = position4;
	}

	public String getPosition5() {
	return position5;
	}

	public void setPosition5(String position5) {
	this.position5 = position5;
	}

	public String getPosition6() {
	return position6;
	}

	public void setPosition6(String position6) {
	this.position6 = position6;
	}
	
	public String buildName(Salutable salutable){
	StringBuilder builder = new StringBuilder();
	for(String namingPart : getAllParts()){
		if(salutable==null && UtilValidator.isEmpty(salutable)){
			return "";
		}
		Object part = UtilReflection.getFieldValue(salutable, namingPart);
		if(part != null)
			builder.append(part.toString()).append(Constants.BLANK_CHAR);
	}
	return builder.toString();
	}
	
	@Transient
	public String[] getNamingParts(){
	return new String[]{position3, position5};
	}

	@Transient
	public String[] getAllParts(){
	return new String[]{position1, position2, position3, position4, position5, position6};
	}

	private static final long serialVersionUID = 1L;
}
