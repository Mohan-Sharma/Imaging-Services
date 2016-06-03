package com.nzion.view.component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.nzion.enums.ListenerEvents;
import com.nzion.util.Constants;
import com.nzion.util.UtilValidator;
import com.nzion.util.ViewUtil;
public class DynamicRowRenderer extends Rows {

	private static final long serialVersionUID = 1L;
	
	public void addRow(Map<String,Object> viewMap,DataBinder binder) throws InstantiationException, IllegalAccessException{
	Component component = null;
	Label lbl = ViewUtil.getLabel(viewMap);
	component = ViewUtil.getComponent(viewMap,binder);
	setEventListeners((ListenerEvents)viewMap.get(Constants.LISTENER_EVENT),component);
	if(component instanceof Textbox)
		/*buildConstraintsForTextBox(component,viewMap)*/;
	createRow(Arrays.asList(lbl,component));
	}
	
	public Row createRow(List<Component> components) {
	Row row = new Row();
	for(Component component : components) {
		row.appendChild(component);
	}
	this.appendChild(row);
	return row;
	}
	
	private void setEventListeners(ListenerEvents listenerEvents,Component component) {
	if(!(component instanceof Textbox) && listenerEvents!=null){
		component.addEventListener(listenerEvents.toString(), new PreferenceListener());
		}
	}

	private final class PreferenceListener implements EventListener {

		public void onEvent(Event event) throws Exception {
		String value = null;
		String lblValue = null;
		Component comp = event.getTarget();
		Label prevLblComp = (Label)comp.getPreviousSibling(); 
		Component prevTxtComp = traversePreviousSiblings(comp);
		if(prevTxtComp!=null && prevTxtComp instanceof Textbox){
			value = ((Textbox)prevTxtComp).getValue();
			lblValue = Integer.parseInt(value)+1 + " to";
			prevLblComp.setValue(lblValue);
		}
		}
		
		public Component traversePreviousSiblings(Component comp){
		String strSeq = (String)comp.getAttribute("seq");
		Component prevComponent = null;
		int seq = 0;
		int prevCompSeq =0;
		if(UtilValidator.isEmpty(strSeq))
			return null;
		seq = Integer.parseInt(strSeq);
		int tempSeq = seq;
		for(Object tempComp : comp.getFellows()){
			Component component = (Component)tempComp;
			 if(component instanceof Textbox && component.getAttribute("seq")!=null){
				 prevCompSeq = Integer.parseInt((String)component.getAttribute("seq")); 
				 if(--tempSeq==prevCompSeq){
					 prevComponent = component;
					 break;
				 }
			 }
			 tempSeq=seq;
		}
		return prevComponent;
		}
	}
}