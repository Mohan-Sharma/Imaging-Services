package com.nzion.view;

import java.io.Serializable;

import com.nzion.domain.Practice;
import com.nzion.domain.UserLogin;
import com.nzion.util.UtilReflection;

public class PracticeViewObject implements Serializable {

	private static final long serialVersionUID = 1L;

    private Practice practice;

	private UserLogin userLogin;

	public PracticeViewObject(Practice practice, UserLogin adminloLogin) {
	this.practice = practice;
	this.userLogin = adminloLogin;
	}

	public Practice getPractice() {
	return practice;
	}

	public void setPractice(Practice practice) {
	this.practice = practice;
	}

	@Override
	public String toString() {
	return UtilReflection.buildStringRepresentation(this).toString();
	}

	public UserLogin getUserLogin() {
	return userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
	this.userLogin = userLogin;
	}

	boolean isSuperAdmin = false;

	public boolean isSuperAdmin() {
	return isSuperAdmin;
	}

	public void setSuperAdmin(boolean isSuperAdmin) {
	this.isSuperAdmin = isSuperAdmin;
	}
}