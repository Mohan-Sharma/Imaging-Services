package com.nzion.enums;

import java.util.Date;

import com.nzion.domain.CalendarRecurrence;
import com.nzion.util.UtilDateTime;

/**
 * @author Sandeep Prusty
 * Mar 29, 2011
 */

@SuppressWarnings("deprecation")
public enum RecurrenceMode {
	DAILLY {
		@Override
		public boolean check(CalendarRecurrence recurrence, Date date) {
		int delta = UtilDateTime.getIntervalInDays(recurrence.getStartDate(), date);
		return (((delta + 1) % recurrence.getQualifier()) == 0);
		}
	},
	WEEKLY {
		@Override
		public boolean check(CalendarRecurrence recurrence, Date date) {
		if(!recurrence.getWeekdays().satisfiedBy(date))
			return false;
		int delta = UtilDateTime.getIntervalInWeeks(recurrence.getStartDate(), date);
		return (((delta + 1) % recurrence.getQualifier()) == 0);
		}
	},
	MONTHLY {
		@Override
		public boolean check(CalendarRecurrence recurrence, Date date) {
		if(recurrence.getDay() != date.getDate())
			return false;
		int delta = UtilDateTime.getIntervalInMonths(recurrence.getStartDate(), date);
		return (((delta + 1) % recurrence.getQualifier()) == 0);
		}
	},
	YEARLY {
		@Override
		public boolean check(CalendarRecurrence recurrence, Date date) {
		if(recurrence.getMonth() != date.getMonth() || recurrence.getDay() != date.getDate())
			return false;
		int delta = UtilDateTime.getIntervalInYears(recurrence.getStartDate(), date);
		return (((delta + 1) % recurrence.getQualifier()) == 0);
		}
	};
	
	public abstract boolean check(CalendarRecurrence recurrence, Date date);
}