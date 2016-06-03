package com.nzion.view.component;

import java.util.Map;
import java.util.Stack;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Toolbarbutton;

import com.nzion.util.Constants;

public class BreadCrumb extends Hbox{

	private static final long serialVersionUID = 1L;
	private static final String  DELIMITER = "->";
	private Stack<Toolbarbutton> breadCrumbStack = new Stack<Toolbarbutton>();
	
	public void addBreadCrumb(String id,String label,EventListener eventListener,Map<?, ?> args) {
	Toolbarbutton toolbarbutton = new Toolbarbutton(label);
	toolbarbutton.setId(id);
	toolbarbutton.setAttribute("args", args);
	toolbarbutton.addEventListener("onClick", new BreadCrumbListener());
	toolbarbutton.addEventListener("onClick", eventListener);
	breadCrumbStack.push(toolbarbutton);
	appendBreadCrumbToDisplay(toolbarbutton);
	}
	
	public void removeBeradCrumb() {
	while(breadCrumbStack.size()>0){
		removeBreadCrumbFromDisplay(breadCrumbStack.pop());
	}
	}
	
	public Integer getBreadCrumbSize() {
	return breadCrumbStack.size();
	}
	
	public Toolbarbutton removeBreadCrumbTop() {
	if(breadCrumbStack.size()==0) return null;
	Toolbarbutton top = breadCrumbStack.pop();
	removeBreadCrumbFromDisplay(top);
	return top;
	}
	
	public Toolbarbutton getBreadCrumbTop() {
	if(breadCrumbStack.size()==0) return null;
	return breadCrumbStack.peek();
	}
	
	private void appendBreadCrumbToDisplay(Toolbarbutton toolbarbutton) {
	if(breadCrumbStack.size()>1) {
		appendChild(createDelimiterLabel(toolbarbutton.getId()));	
	}
	appendChild(toolbarbutton);
	}
	
	private Label createDelimiterLabel(String id) {
	Label label = new Label(DELIMITER);
	label.setId(Constants.LABEL_PREFIX+id);
	return label;
	}

	public void addBreadCrumb(String label,EventListener eventListener,Map<?, ?> args) {
	addBreadCrumb(label, label, eventListener, args);
	}
	
	private void removeBreadCrumbFromDisplay(Toolbarbutton removeableBreadCrumb) {
	if(breadCrumbStack.size()>=1) {
		this.removeChild(removeableBreadCrumb.getFellowIfAny(Constants.LABEL_PREFIX+removeableBreadCrumb.getId()));	
	}

	this.removeChild(removeableBreadCrumb);
	}
	
	private class BreadCrumbListener implements EventListener{
		@Override
		public void onEvent(Event event) throws Exception {
		String id = event.getTarget().getId();
		while(!id.equals(breadCrumbStack.peek().getId())){
		BreadCrumb.this.removeBreadCrumbFromDisplay(breadCrumbStack.pop()); 
		}
		}
	}
}
