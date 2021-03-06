package com.nzion.util;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;

/**
 * @author Sandeep Prusty
 * May 13, 2010
 */
public class UtilDisplay {

	public static String camelcaseToUiString(String arg){
	StringBuilder stringBuilder = new StringBuilder();
	arg = StringUtils.capitalize(arg);
	for(char ch : arg.toCharArray()){
		if(Character.isUpperCase(ch))
			stringBuilder.append(' ');
		stringBuilder.append(ch);
	}
	return stringBuilder.toString();
	}	

	public static String camelcaseToUiForCopositeFiedl(String arg){
	return (arg.indexOf('.') == -1) ? camelcaseToUiString(arg) : camelcaseToUiString(arg.substring(arg.lastIndexOf('.') + 1, arg.length()));
	}	
	
	public static String uiStringToCamelCase(String arg){
	arg = arg.replaceAll(" ", "");
	return StringUtils.uncapitalize(arg);
	}
	
	public static String buildIdFromName(String prefix, String name){
	return prefix + name.replaceAll(" ", "");
	}
	
	public static String uncapitalizeFirstAlphabet(String className) {
	if(UtilValidator.isEmpty(className))
		return null;
	return className.substring(0, 1).toLowerCase().concat(className.substring(1));
	}
	public static String capitalizeFirstAlphabet(String className) {
	return className.substring(0, 1).toUpperCase().concat(className.substring(1));
	}
	
	public static String getPropertyNameFromComponentId(String componentId){
		return getPropertyNameFromComponentId(componentId,null);
	}
	
	public static String getPropertyNameFromComponentId(String componentId,Short componentPrefixLength){
	return UtilValidator.isEmpty(componentId) ? null : componentId.substring(componentPrefixLength==null?3:componentPrefixLength);
	}
	
	public static String getLabelIdFromPropertyName(String propertyName){
	return UtilValidator.isEmpty(propertyName) ? null : Constants.LABEL_PREFIX+propertyName;
	}
	
	public static String getTextboxIdFromPropertyName(String propertyName){
	return UtilValidator.isEmpty(propertyName) ? null : Constants.TEXTBOX_PREFIX+propertyName;
	}
	public static String getTextboxIdFromLabelId(String labelId){
	return UtilValidator.isEmpty(labelId) ? null : Constants.TEXTBOX_PREFIX+getPropertyNameFromComponentId(labelId);
	}
	public static String getLabelIdFromTextBoxId(String textBoxId){
	return UtilValidator.isEmpty(textBoxId) ? null : Constants.LABEL_PREFIX+getPropertyNameFromComponentId(textBoxId);
	}
	public static void validateOnlyAlphabets(Textbox textbox){
	if(!UtilValidator.validateOnlyAlphabets(textbox.getValue()))
		throw new WrongValueException(textbox, "Enter only alphabets");
	}
	public static void validateOnlyDigits(Textbox textbox){
	if(UtilValidator.isEmpty(textbox.getValue()))
		return;
	if(!UtilValidator.validateOnlyDigits(textbox.getValue()))
		throw new WrongValueException(textbox, "Enter only digits");
	}
	
	public static void validateNonDigits(Textbox textbox){
	if(!UtilValidator.validateNonDigits(textbox.getValue()))
		throw new WrongValueException(textbox, "Digits not allowed");
	}
	public static void validateAlphaNumeric(Textbox textbox){
	if(!UtilValidator.validateAlphaNumeric(textbox.getValue()))
		throw new WrongValueException(textbox, "Special Characters not allowed");
	}
	public static void validateAlphabetsAndDots(Combobox combobox){
	if(!UtilValidator.validateAlphabetsAndDots(combobox.getValue()))
		throw new WrongValueException(combobox, "Enter only alphabets and dot");
	}
	public static void validateCharacters(Textbox textbox){
	if(!UtilValidator.validateCharacters(textbox.getValue()))
		throw new WrongValueException(textbox, "Must contain aleast one letter,one special character,one digit with minimum 8 characters");
	}
	
	public static void validateNineDigits(Textbox textbox){
	if(!UtilValidator.validateNineDigits(textbox.getValue()))
		throw new WrongValueException(textbox, "Must contain nine digits only");
	}
	public static void validateOnlyAlphabetsAndWhiteSpaces(Textbox textbox){
	if(!UtilValidator.validateOnlyAlphabetsAndWhiteSpaces(textbox.getValue()))
		throw new WrongValueException(textbox, "Must contain only Alphabets and spaces");
	}
	
	public static void validateOnlyAlphaNumericAndWhiteSpaces(Textbox textbox){
	textbox.setValue(textbox.getValue().trim());
	if(!UtilValidator.validateOnlyAlphaNumericAndWhiteSpaces(textbox.getValue()))
		throw new WrongValueException(textbox, "Must contain only Alphanumeric and spaces");
	}
	
	public static String appendStrings(String string1,String delimiter,String string2){
	if(UtilValidator.isEmpty(string1))
		return string2;
	if(UtilValidator.isEmpty(string2))
		return string1;
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append(string1);
	stringBuilder.append(delimiter);
	stringBuilder.append(string2);
	return stringBuilder.toString();
	}
	
	public static String clobToString(Clob clob) throws SQLException{
	if(clob == null)
		return null;
	return clob.getSubString(1l, (int) clob.length());
	}
	
	public static void validateFiveDigits(Textbox textbox){
		if(textbox.getValue().trim().length() < 5)
			throw new WrongValueException(textbox , "Provide valid information");
	}
	
	
	public static void validateDateRange(Datebox startDatebox, Datebox endDatebox){
	if(startDatebox.getValue() == null || endDatebox.getValue() == null)
		return;
	if(endDatebox.getValue().compareTo(startDatebox.getValue()) < 0)
		throw new WrongValueException(endDatebox, "End date should be after start date");
	}

	public static void validateTimeRange(Timebox startTimebox, Timebox endTimebox){
	if(startTimebox.getValue() == null || endTimebox.getValue() == null)
		return;
	if(UtilDateTime.timeOnly(endTimebox.getValue()).compareTo(UtilDateTime.timeOnly(startTimebox.getValue())) < 0)
		throw new WrongValueException(endTimebox, "End time should be after start time");
	}
	public static void validateDigitOnly(Textbox textbox){
	if(UtilValidator.isNotEmpty(textbox.getValue()))
	 if(!UtilValidator.validateOnlyDigits(textbox.getValue()))
		 throw new WrongValueException(textbox, "Please Provide valid Office Ext number");
	}
	
	public static String style(String resulStatus){
	String style = "font-weight:bold;background-color:orange";
	if(StringUtils.isNotEmpty(resulStatus) && !"N".equalsIgnoreCase(resulStatus))
		return style;
	return "";
	}
	
	public static Date getMeaningfulUseBeginDate(String reportingTimePeriod){
	Date periodStartDate = null;
	if (UtilValidator.isNotEmpty(reportingTimePeriod)) {
		Date currentDate = new Date();
		currentDate = UtilDateTime.getDayEnd(currentDate);
		if ("30 Days".equalsIgnoreCase(reportingTimePeriod))
			periodStartDate = UtilDateTime.getDayStart(UtilDateTime.addDaysToDate(currentDate, -30));
		if ("60 Days".equalsIgnoreCase(reportingTimePeriod))
			periodStartDate = UtilDateTime.getDayStart(UtilDateTime.addDaysToDate(currentDate, -60));
		if ("90 Days".equalsIgnoreCase(reportingTimePeriod))
			periodStartDate = UtilDateTime.getDayStart(UtilDateTime.addDaysToDate(currentDate, -90));
		if ("6 Months".equalsIgnoreCase(reportingTimePeriod))
			periodStartDate = UtilDateTime.getDayStart(UtilDateTime.addMonthsToDate(currentDate, -6));
		if ("1 Year".equalsIgnoreCase(reportingTimePeriod))
			periodStartDate = UtilDateTime.getDayStart(UtilDateTime.addYearsToDate(currentDate, -1));
	}
	return periodStartDate;
	}
	
	public static  boolean validateVaccineAge(Intbox lowEndIntbox,Intbox highEndIntbox,Combobox lowEndCombobox,Combobox highEndCombobox){
  	String lowEndTimePeriod = (String)lowEndCombobox.getSelectedItem().getValue();
  	String highEndTimePeriod = (String)highEndCombobox.getSelectedItem().getValue();
  	int lowEndAge = lowEndIntbox.getValue()== null?0:lowEndIntbox.getValue();
  	int highEndAge = highEndIntbox.getValue() == null?0:highEndIntbox.getValue();
  	if("Month".equalsIgnoreCase(lowEndTimePeriod))
  		lowEndAge = lowEndAge * 4;
  	if("Year".equalsIgnoreCase(lowEndTimePeriod))
  		lowEndAge = lowEndAge * 52;
  	if("Month".equalsIgnoreCase(highEndTimePeriod))
  		highEndAge = highEndAge * 4;
  	if("Year".equalsIgnoreCase(highEndTimePeriod))
  		highEndAge = highEndAge * 52;
  	if(lowEndAge > highEndAge){
  		throw new WrongValueException(highEndIntbox,"Maxmium Age Range Must Be Greater Than Minimum Age");
  	}
  	if(lowEndAge > highEndAge)
  		return true;
  	return false;
  }
	
}