package com.nzion.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table
public class Department extends IdGeneratingBaseEntity{

		private String departmentCode;
		
		private String departmentDescription;

		public String getDepartmentCode() {
			return departmentCode;
		}

		public void setDepartmentCode(String departmentCode) {
			this.departmentCode = departmentCode;
		}

		public String getDepartmentDescription() {
			return departmentDescription;
		}

		public void setDepartmentDescription(String departmentDescription) {
			this.departmentDescription = departmentDescription;
		}
		
		
}
