package com.nzion.util;

import java.util.Date;

import com.nzion.domain.Person;
import com.nzion.domain.Practice;
import com.nzion.domain.Roles;
import com.nzion.domain.UserLogin;
import com.nzion.domain.annot.AccountNumberField;

public class DomainBeansUtil {

	public static String getPropertyNameForAccountNumber(Class<?> klass) {
	AccountNumberField accountNumberField = klass.getAnnotation(AccountNumberField.class);
	String columnName = null;
	if (accountNumberField != null) {
		columnName = accountNumberField.value();
	}
	return columnName;
	}

	public static Person getDummyAdminLogin(UserLogin userLogin) {
	return DomainBeansUtil.getDummyAdminLogin(userLogin,null);
	}

	public static Person getDummyAdminLogin(UserLogin userLogin, Practice practice) {
	if (userLogin != null && userLogin.getPerson() != null) return userLogin.getPerson();
	if (userLogin == null || practice == null)
		throw new IllegalArgumentException("userLogin and practice cannot be null");
	Person person = new Person();
	person.setFirstName("THE");
	person.setAccountNumber("practiceadmin");
	person.setLastName("Administrator");
	userLogin.setPerson(person);
	userLogin.addRole(Roles.ADMIN);
	person.setUserLogin(userLogin);
	userLogin.setPwdChangedTime(new Date());
	return person;
	}
}
