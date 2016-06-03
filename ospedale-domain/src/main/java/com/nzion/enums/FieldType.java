package com.nzion.enums;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;

public enum FieldType {
	ENUM, ENTITY, IDSEQUENCE, COLORBOX, LABEL,INTBOX,
	DATEBOX {
		@Override
		public String toString() {
		return "Date";
		}

		@Override
		public Object parse(String arg) {
		try {
			if(UtilValidator.isEmpty(arg))
				return null;
			SimpleDateFormat  sd = new SimpleDateFormat(UtilDateTime.DEFAULT_TO_STRING_DATEFORMAT);
			return sd.parse(arg);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}		
		}
	},

	TEXTBOX {
		@Override
		public String toString() {
		return "Text";
		}
	},
	CHECKBOX {
		@Override
		public String toString() {
		return "Boolean";
		}
	};
	
	public Object parse(String arg){
	return arg;
	}

	public static FieldType[] fetchAllowedCustomFormFieldTypes() {
	return new FieldType[] { TEXTBOX, CHECKBOX, DATEBOX };
	}
}