/*
 * header file
 */
package com.nzion.enums;

import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Enum WEEKDAY.
 * 
 * @author Sandeep Prusty Apr 1, 2010
 */
public enum WEEKDAY {

	SUNDAY(1), MONDAY(2), TUESDAY(4), WEDNESDAY(8), THURSDAY(16),  FRIDAY(32), SATURDAY(64);

    /** The value. */
    private int value;

    /** This map hold java convention of weekday-number to this enum values */
    private static Map<Integer, WEEKDAY> map = new HashMap<Integer, WEEKDAY>();
    
    static{
	    map.put(new Integer(1), SUNDAY);
	    map.put(new Integer(2), MONDAY);
	    map.put(new Integer(3), TUESDAY);
	    map.put(new Integer(4), WEDNESDAY);
	    map.put(new Integer(5), THURSDAY);
	    map.put(new Integer(6), FRIDAY);
	    map.put(new Integer(7), SATURDAY);
    }
    
    /**
     * Instantiates a new wEEKDAY.
     * 
     * @param value
     *            the value
     */
    private WEEKDAY(int value) {
	this.value = value;
    }

    /**
     * Contains.
     * 
     * @param weekdays
     *            the weekdays
     * @param weekday
     *            the weekday
     * @return true, if successful
     */
    public static boolean contains(int weekdays, WEEKDAY weekday) {
	return weekday.val() == (weekdays & weekday.val());
    }

    /**
     * Turn off.
     * 
     * @param weekdays
     *            the weekdays
     * @param weekday
     *            the weekday
     * @return the int
     */
    public static int turnOff(int weekdays, WEEKDAY weekday) {
	return (weekdays & (~weekday.val()));
    }

    /**
     * Turn on.
     * 
     * @param weekdays
     *            the weekdays
     * @param weekday
     *            the weekday
     * @return the int
     */
    public static int turnOn(int weekdays, WEEKDAY weekday) {
	return (weekdays | weekday.val());
    }
    
    public static WEEKDAY get(int dayOfWeek){
    return map.get(new Integer(dayOfWeek));
    }

    /**
     * Val.
     * 
     * @return the int
     */
    public int val() {
	return value;
    }
}
