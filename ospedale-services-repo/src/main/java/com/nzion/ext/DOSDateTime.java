/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nzion.ext;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;




/**
 *
 * @author Neill Harper
 */
public class DOSDateTime extends ZIPObject {
    
    private Date classicDate;
    private short dosDate;
    private short dosTime;
    
    public DOSDateTime()
    {
    
    }
    
    public DOSDateTime(Date classic)
    {
        this.classicDate = classic;
        this.updateDOSDateTime();
    }
    
    public DOSDateTime(short DOSDate, short DOSTime)
    {
        this.dosDate = DOSDate;
        this.dosTime = DOSTime;
        this.updateClassicDateTime();
    }

    @Override
    protected byte[] GetOutput() {
        ByteBuffer buff = ByteBuffer.allocate(4);
        buff.order(ByteOrder.LITTLE_ENDIAN);
        buff.putShort(dosTime);
        buff.putShort(dosDate);
        
        return buff.array();
    }

    public Date getClassicDate() {
        return classicDate;
    }

    public void Date(Date classicDate) {
        this.classicDate = classicDate;
        updateDOSDateTime();
    }

    public short getDosDate() {
        return dosDate;
    }

    public void setDosDate(short dosDate) {
        this.dosDate = dosDate;
        updateClassicDateTime();
    }

    public short getDosTime() {
        return dosTime;
    }

    public void setDosTime(short dosTime) {
        this.dosTime = dosTime;
        updateClassicDateTime();
    }

    private int GetBits(short value, int startBit, int bitCount) {
        short result;
        result = (short)(value << (startBit));
        result = (short)(result >> (15-bitCount));
        
        return result;
    }

    private void updateClassicDateTime() {
        int day  = GetBits(dosDate, 0 , 5);
        int month = GetBits(dosDate,5,4);
        int year = GetBits(dosDate,9,7) + 1980;
        
        int seconds = GetBits(dosTime,0,5)/2;
        int minutes = GetBits(dosTime,5,6);
        int hour = GetBits(dosTime,11,5);
        
        classicDate = new  Date(dosDate + dosTime);
        //classicDate = new Date(year, month, day,hour,minutes,seconds);
    }

    private void updateDOSDateTime() {
        
        short workDate = (short)(((classicDate.getYear() - 80))<<9);
        workDate |= (short)(((classicDate.getMonth() +1) & 0xf) << 5);
        workDate |= (short)(classicDate.getDate() & 0x1f);
        
        this.dosDate = workDate;
        
        short workTime = (short)((classicDate.getHours() & 0x1f) << 11);
        workTime |= (short)((classicDate.getMinutes() & 0x3f) << 5);
        workTime |= (short)((classicDate.getSeconds() * 2) & 0x1f);
        
        this.dosTime = workTime;
    }

}
