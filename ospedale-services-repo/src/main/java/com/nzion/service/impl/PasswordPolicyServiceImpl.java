package com.nzion.service.impl;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nzion.domain.PasswordPolicy;
import com.nzion.domain.Practice;
import com.nzion.domain.UserLogin;
import com.nzion.repository.PasswordPolicyRepository;
import com.nzion.service.PasswordPolicyService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDateTime;

@Service("passwordPolicyService")
@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
public class PasswordPolicyServiceImpl implements PasswordPolicyService {

	private PasswordPolicyRepository repository;
	
	@Resource(name="passwordPolicyRepo")
	@Required
	public void setRepository(PasswordPolicyRepository repository) {
	this.repository = repository;
	}

	public PasswordPolicy getEffectivePasswordPolicy() {
	return repository.getPasswordPolicy();
	}

	public void saveOrUpdate(PasswordPolicy policy) {
	repository.save(policy);
	}
	
	public PasswordPolicy getDefaultPasswordPolicy(){
	return repository.getDefaultPasswordPolicy();
	}

	public boolean checkWarningPeriodArrival(UserLogin userLogin){
	PasswordPolicy policy = getEffectivePasswordPolicy();
	if(policy.getPwdExpireWarning() == null || !policy.getPwdExpireWarning())
		return false;
	Date pwdChangedTimeStamp =  userLogin.getPwdChangedTime();
	Integer pwdMaxAge=30;
	if(policy.getPwdmaxage()!=null)
		pwdMaxAge=policy.getPwdmaxage();
	Date thru = UtilDateTime.addDaysToDate(pwdChangedTimeStamp, pwdMaxAge);
	long diff = UtilDateTime.getIntervalInHours(new Date(), thru);
	return diff < 48;
	}
	
	public boolean checkPasswordExpiration(UserLogin userLogin){
	PasswordPolicy policy = getEffectivePasswordPolicy();
	if(userLogin.isRequirePasswordChange() != null && userLogin.isRequirePasswordChange())
		return true;
	Integer pwdMaxExpireAge = policy.getPwdmaxage();
	if(pwdMaxExpireAge==null)
		return false;
	Integer pwdAge = UtilDateTime.getIntervalInDays(userLogin.getPwdChangedTime(), new Date());
	return ( pwdAge > pwdMaxExpireAge );
	}
	
	@Override
	public boolean validatePassword(String password) {
	PasswordPolicy policy = getEffectivePasswordPolicy();
	if(policy == null || policy.getPwdchecksyntax() == null || !policy.getPwdchecksyntax())
		return true;
	Pattern pattern = Pattern.compile(policy.getSelectedPattern());
	Matcher matcher = pattern.matcher(password);
	return password.length() >= policy.getPwdminlength() && matcher.matches();
	}
	
	public static void main(String[] args) {
	//Pattern pattern = Pattern.compile("^([(\\p{Alpha})+(\\p{Punct})+])+$");
	
//	String v1="[\p{Alnum}]+";
//	String v2="[\p{Alpha}\p{Punct}]+";
//	String v3="[\p{Alnum}\p{Punct}]+";
//	String v4="[0-9\p{Punct}]+";
	String a = "((\\p{Alpha})+(\\p{Punct})+)+";
	String e = "((\\p{Alpha})*(\\p{Punct})*)*";
	String b = "((\\p{Punct})+(\\p{Alpha})+)+";
//	String a1 = 
	//alphabets and numbers
	String alphaNumbers = "([0-9]+(\\p{Alpha})+)+([0-9]*(\\p{Alpha})*)*|((\\p{Alpha})+[0-9]+)([0-9]*(\\p{Alpha})*)*";
	//alphabets and special characters
	String alphaCharacters = "((\\p{Alpha})+(\\p{Punct})+)+"+"((\\p{Alpha})*(\\p{Punct})*)*"+"|"+"((\\p{Punct})+(\\p{Alpha})+)+"+"((\\p{Alpha})*(\\p{Punct})*)*";
	String f = "((\\p{Alpha})+(\\p{Punct})+)+((\\p{Alpha})*(\\p{Punct})*)*|((\\p{Punct})+(\\p{Alpha})+)((\\p{Alpha})*(\\p{Punct})*)*" ;
	//digits and special characters
	String g = "((\\d)+(\\p{Punct})+)+((\\d)*(\\p{Punct})*)*|((\\p{Punct})+(\\d)+)+((\\d)*(\\p{Punct})*)*" ;
	//alphabets digits and special characters
	String h = "((\\p{Alpha})+[0-9]+(\\p{Punct})+)+((\\p{Alpha})*[0-9]*(\\p{Punct})*)* |((\\p{Punct})+[0-9]+(\\p{Alpha})+)+((\\p{Alpha})*[0-9]*(\\p{Punct})*)*" ;
//    String h = "([0-9+\\p{Alpha}+\\p{Punct}+]+)+";
	String a1 = "((\\p{Alpha})+(\\d)+(\\p{Punct})+)+((\\p{Alpha})*(\\d)*(\\p{Punct})*)*";
	String b1 = "((\\p{Alpha})+(\\p{Punct})+(\\d)+)+((\\p{Alpha})*(\\d)*(\\p{Punct})*)*";
	String c1 = "((\\d)+(\\p{Alpha})+(\\p{Punct})+)+((\\p{Alpha})*(\\d)*(\\p{Punct})*)*";
	String d1 = "((\\d)+(\\p{Punct})+(\\p{Alpha})+)+((\\p{Alpha})*(\\d)*(\\p{Punct})*)*";
	String e1 = "((\\p{Punct})+(\\p{Alpha})+(\\d)+)+((\\p{Alpha})*(\\d)*(\\p{Punct})*)*";
	String f1 = "((\\p{Punct})+(\\d)+(\\p{Alpha})+)+((\\p{Alpha})*(\\d)*(\\p{Punct})*)*";
//	String finalString = a1+"|"+b1+"|"+c1+"|"+d1+"|"+e1+"|"+f1;
	String finalString = "((\\p{Alpha})+(\\d)+(\\p{Punct})+)+((\\p{Alpha})*(\\d)*(\\p{Punct})*)*|((\\p{Alpha})+(\\p{Punct})+(\\d)+)+((\\p{Alpha})*(\\d)*(\\p{Punct})*)*|((\\d)+(\\p{Alpha})+(\\p{Punct})+)+((\\p{Alpha})*(\\d)*(\\p{Punct})*)*|((\\d)+(\\p{Punct})+(\\p{Alpha})+)+((\\p{Alpha})*(\\d)*(\\p{Punct})*)*|((\\p{Punct})+(\\p{Alpha})+(\\d)+)+((\\p{Alpha})*(\\d)*(\\p{Punct})*)*|((\\p{Punct})+(\\d)+(\\p{Alpha})+)+((\\p{Alpha})*(\\d)*(\\p{Punct})*)*";
	String c = a+e+"|"+b+e;
	String d ="[a-zA-Z]{1,}|[!-/:-@\\[-`{-~]{1,}";
	Pattern pattern = Pattern.compile(finalString);
	Matcher matcher = pattern.matcher("&^*&");
	}
}
