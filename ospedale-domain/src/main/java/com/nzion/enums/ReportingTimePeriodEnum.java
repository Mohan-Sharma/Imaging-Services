package com.nzion.enums;

import java.util.Calendar;
import java.util.Date;

public enum ReportingTimePeriodEnum {

	LAST30DAYS {
		public String toString() {
		return "Last 30 Days";
		}

		@Override
		public Date add(Date reference) {
		return add(Calendar.DAY_OF_MONTH, reference, -30);
		}
	},
	LAST60DAYS {
		public String toString() {
		return "Last 60 Days";
		}

		@Override
		public Date add(Date reference) {
		return add(Calendar.DAY_OF_MONTH, reference, -60);
		}
	},
	LAST90DAYS {
		public String toString() {
		return "Last 90 Days";
		}

		@Override
		public Date add(Date reference) {
		return add(Calendar.DAY_OF_MONTH, reference, -90);
		}
	},
	LAST6MONTHS {
		public String toString() {
		return "Last 6 Months";
		}

		@Override
		public Date add(Date reference) {
		return add(Calendar.MONTH, reference, -6);
		}
	},
	LAST1YEAR {
		public String toString() {
		return "Last 1 Year";
		}

		@Override
		public Date add(Date reference) {
		return add(Calendar.YEAR, reference, -1);
		}
	},
	CUSTOM {
		public String toString() {
		return "Custom";
		}

		@Override
		public Date add(Date reference) {
		return null;
		}
	};
	
	public static Date add(int calendarField, Date date, int value){
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	calendar.add(calendarField, value);
	Date result = calendar.getTime();
	return result;
	}
	
	public abstract Date add(Date reference);
}
