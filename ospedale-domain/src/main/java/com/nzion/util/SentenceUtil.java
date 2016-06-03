package com.nzion.util;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.nzion.domain.util.Inflector;
import com.nzion.enums.AnswerType;

public class SentenceUtil {
	public static final String SPACE = " ";
	public static final String EMPTY_STRING = "";
	public static final String COMMA = ",";
	public static final String FULL_STOP = ".";
	public static final String AND = "and";
	public static final String NEW_LINE = "\n";
	public static final String COLON = ":";
	public static final String ARE = "are";
	public static final String IS = "is";

	public static String generateName(String firstName, String middleName, String lastName) {
	StringBuilder nameBuilder = new StringBuilder(EMPTY_STRING);
	if (UtilValidator.isNotEmpty(firstName)) {
		nameBuilder.append(cleanName(firstName));
	}
	if (UtilValidator.isNotEmpty(middleName) && UtilValidator.isNotEmpty(nameBuilder.toString())) {
		nameBuilder.append(SPACE + cleanName(middleName));
	} else
		if (UtilValidator.isNotEmpty(middleName) && UtilValidator.isEmpty(nameBuilder.toString())) {
			nameBuilder.append(cleanName(middleName));
		}
	if (UtilValidator.isNotEmpty(lastName) && UtilValidator.isNotEmpty(nameBuilder.toString())) {
		nameBuilder.append(SPACE + cleanName(lastName));
	} else
		if (UtilValidator.isNotEmpty(lastName) && UtilValidator.isEmpty(nameBuilder.toString())) {
			nameBuilder.append(cleanName(lastName));
		}
	return nameBuilder.toString();
	}

	private static String cleanName(String name) {
	name = name.trim();
	name = name.substring(0, 1).toUpperCase() + name.substring(1);
	return name;
	}

	public static String cleanParagraph(String paragraph) {
	if (UtilValidator.isEmpty(paragraph)) {
		return EMPTY_STRING;
	}
	String[] sentences = paragraph.split(FULL_STOP);
	StringBuilder builder = new StringBuilder();
	for (String sentence : sentences) {
		sentence = sentence.trim();
		sentence = sentence.substring(0, 1).toUpperCase() + sentence.substring(1);
		if (builder.length() != 0) {
			builder.append(SPACE);
		}
		builder.append(sentence + FULL_STOP);
	}
	return builder.toString();
	}

	public static String capitalizeFirstLetter(String s) {
	if (UtilValidator.isNotEmpty(s)) {
		s = s.trim();
		if (!Character.isUpperCase(s.charAt(0))) {
			s = s.substring(0, 1).toUpperCase() + s.substring(1);
		}
	}
	return s;
	}

	public static String generateAgeStr(String age) {
	StringBuilder ageBuilder = new StringBuilder(EMPTY_STRING);
	ageBuilder.append(age + " old");
	return ageBuilder.toString();
	}

	public static String separateListValues(Collection<String> values, String separator, boolean appendAnd) {
	if (UtilValidator.isEmpty(values)) {
		return EMPTY_STRING;
	}
	if (UtilValidator.isEmpty(separator)) {
		separator = COMMA; // Default separator.
	}
	int i = 1;
	int size = values.size();
	StringBuilder valuesBuffer = new StringBuilder();
	for (String value : values) {
		if (i == size) {
			valuesBuffer.append(SPACE + value);
		} else
			if (appendAnd && i == size - 1) {
				valuesBuffer.append(SPACE + value + SPACE + AND);
			} else {
				valuesBuffer.append(SPACE + value + separator);
			}
		i++;
	}
	return valuesBuffer.toString();
	}

	/**
	 * Uses Comma as defualt separator.
	 * @param values
	 * @return
	 */
	public static String separateListValues(Collection<String> values) {
	return separateListValues(values, COMMA, true);
	}

	public static String separateListValues(Collection<String> values, boolean appendAnd) {
	return separateListValues(values, COMMA, appendAnd);
	}

	public static String getSingularPluralWord(int count, String singularWord, String pluralWord) {
	if (singularWord == null && pluralWord == null) {
		return "";
	}
	String output;
	if (count == 1) {
		if (singularWord == null) {
			output = Inflector.getInstance().singularize(pluralWord);
		} else {
			output = singularWord;
		}
	} else {
		if (pluralWord == null) {
			output = Inflector.getInstance().pluralize(singularWord);
		} else {
			output = pluralWord;
		}
	}
	return output;
	}

	public static String appendFullStop(String s) {
	s = s.trim();
	if (s.endsWith(".")) {
		return s;
	} else {
		return s + FULL_STOP;
	}
	}

	public static String getIsOrAre(int size) {
	return getSingularPluralWord(size, ARE, IS);
	}

	/**
	 * Currently this method works only till billions.
	 * @param number
	 * @return
	 */
	public static String convertNumberToEnglishWord(long number) {
	return EnglishNumberToWords.convert(number);
	}

	/*public static String buildSentenceForQAs(Set<PatientQuestionAnswer> qas) {
	StringBuilder buffer = new StringBuilder();
	if (UtilValidator.isEmpty(qas)) {
		return "";
	}
	for (PatientQuestionAnswer qa : qas) {
		String s = null;
		if (AnswerType.MULTIPLE_CHOICE.equals(qa.getQuestion().getAnswerType())) {
				s = qa.getSentence();
				if(UtilValidator.isNotEmpty(qa.getAnswerString()) && UtilValidator.isNotEmpty(s)){
				buffer.append(s);
				}
		} else {
			if (AnswerType.SINGLE_CHOICE.equals(qa.getQuestion().getAnswerType())) {
				s = StringUtils.isEmpty(qa.getSentence()) ? qa.getQuestion().getDesc() + ":" : qa.getSentence();
				if(UtilValidator.isNotEmpty(qa.getAnswerString()) && UtilValidator.isNotEmpty(s)){
				buffer.append(s).append("\n");
				}
			}
				if(AnswerType.YES_NO.equals(qa.getQuestion().getAnswerType())){
				s = StringUtils.isEmpty(qa.getSentence()) ? qa.getQuestion().getDesc() + ":" : qa.getSentence();
				if(UtilValidator.isNotEmpty(qa.getAnswerString()) && UtilValidator.isNotEmpty(s))
				buffer.append(s).append(". ");
				}
				if(AnswerType.FREE_TEXT.equals(qa.getQuestion().getAnswerType()))
					buffer.append(qa.getAnswerString()).append(".");
		}
	}
	return buffer.toString();
	}*/

}
