package com.nzion.zkoss.composer.appointment;

import java.text.SimpleDateFormat;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import com.nzion.domain.CalendarSlot;

public class SlotConverter implements TypeConverter  {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
	// TODO Auto-generated method stub
	return null;
	}

	@Override
	public Object coerceToUi(Object val, Component listitem) {
	if(val==null)return null;
	StringBuilder buffer = new StringBuilder();
	if(val instanceof CalendarSlot) {
		CalendarSlot slot = (CalendarSlot)val;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		buffer.append(sdf.format(slot.getStartTime()));
		buffer.append(" - ");
		buffer.append(sdf.format(slot.getEndTime()));
	}
	return buffer.toString();
	}

}
