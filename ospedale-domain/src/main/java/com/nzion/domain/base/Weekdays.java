/*
 * header file
 */
package com.nzion.domain.base;

import static com.nzion.enums.WEEKDAY.FRIDAY;
import static com.nzion.enums.WEEKDAY.MONDAY;
import static com.nzion.enums.WEEKDAY.SATURDAY;
import static com.nzion.enums.WEEKDAY.SUNDAY;
import static com.nzion.enums.WEEKDAY.THURSDAY;
import static com.nzion.enums.WEEKDAY.TUESDAY;
import static com.nzion.enums.WEEKDAY.WEDNESDAY;
import static com.nzion.enums.WEEKDAY.contains;
import static com.nzion.enums.WEEKDAY.turnOff;
import static com.nzion.enums.WEEKDAY.turnOn;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.nzion.enums.WEEKDAY;

// TODO: Auto-generated Javadoc
/**
 * The Class Weekdays.
 * 
 * @author Sandeep Prusty Apr 1, 2010
 */

@Embeddable
public class Weekdays {

    /** The weekdays. */
    private int weekdays;

    /**
     * Gets the weekdays.
     * 
     * @return the weekdays
     */
    @Column
    public int getWeekdays() {
	return weekdays;
    }

    /**
     * Checks if is friday.
     * 
     * @return true, if is friday
     */
    @Transient
    public boolean isFriday() {
	return contains(weekdays, FRIDAY);
    }

    /**
     * Checks if is monday.
     * 
     * @return true, if is monday
     */
    @Transient
    public boolean isMonday() {
	return contains(weekdays, MONDAY);
    }

    /**
     * Checks if is saturday.
     * 
     * @return true, if is saturday
     */
    @Transient
    public boolean isSaturday() {
	return contains(weekdays, SATURDAY);
    }

    /**
     * Checks if is sunday.
     * 
     * @return true, if is sunday
     */
    @Transient
    public boolean isSunday() {
	return contains(weekdays, SUNDAY);
    }

    /**
     * Checks if is thursday.
     * 
     * @return true, if is thursday
     */
    @Transient
    public boolean isThursday() {
	return contains(weekdays, THURSDAY);
    }

    /**
     * Checks if is tuesday.
     * 
     * @return true, if is tuesday
     */
    @Transient
    public boolean isTuesday() {
	return contains(weekdays, TUESDAY);
    }

    /**
     * Checks if is wednesday.
     * 
     * @return true, if is wednesday
     */
    @Transient
    public boolean isWednesday() {
	return contains(weekdays, WEDNESDAY);
    }

    /**
     * Sets the friday.
     * 
     * @param on
     *            the new friday
     */
    public void setFriday(boolean on) {
	setWeekday(on, FRIDAY);
    }

    /**
     * Sets the monday.
     * 
     * @param on
     *            the new monday
     */
    public void setMonday(boolean on) {
	setWeekday(on, MONDAY);
    }

    /**
     * Sets the saturday.
     * 
     * @param on
     *            the new saturday
     */
    public void setSaturday(boolean on) {
	setWeekday(on, SATURDAY);
    }

    /**
     * Sets the sunday.
     * 
     * @param on
     *            the new sunday
     */
    public void setSunday(boolean on) {
	setWeekday(on, SUNDAY);
    }

    /**
     * Sets the thursday.
     * 
     * @param on
     *            the new thursday
     */
    public void setThursday(boolean on) {
	setWeekday(on, THURSDAY);
    }

    /**
     * Sets the tuesday.
     * 
     * @param on
     *            the new tuesday
     */
    public void setTuesday(boolean on) {
	setWeekday(on, TUESDAY);
    }

    /**
     * Sets the wednesday.
     * 
     * @param on
     *            the new wednesday
     */
    public void setWednesday(boolean on) {
	setWeekday(on, WEDNESDAY);
    }

    /**
     * Sets the weekday.
     * 
     * @param on
     *            the on
     * @param weekday
     *            the weekday
     */
    private void setWeekday(boolean on, WEEKDAY weekday) {
	weekdays = on ? turnOn(weekdays, weekday) : turnOff(weekdays, weekday);
    }

    /**
     * You should never call this explicitly. Provided for hibernate to use.
     * Setting an int here may cause unwanted results. Rather use
     * setXxxday(boolean b) method.
     * 
     * @param weekdays
     *            the new weekdays
     */
    public void setWeekdays(int weekdays) {
	this.weekdays = weekdays;
    }
    
    public boolean satisfiedBy(Date givenDate){
    if(givenDate == null) return false;
    Calendar javaUtilCalendar = Calendar.getInstance();
    javaUtilCalendar.setTime(givenDate);
    return contains(weekdays, WEEKDAY.get(javaUtilCalendar.get(Calendar.DAY_OF_WEEK)));
    }
    
    public boolean isColliding(Weekdays another){
    return (weekdays & another.weekdays) != 0;
    }
    
    public static Weekdays allSelectedWeekdays(){
    Weekdays weekdays = new Weekdays();
    weekdays.setWeekdays(1 + 2 + 4 + 8 + 16 + 32 + 64);
    return weekdays;
    }
}