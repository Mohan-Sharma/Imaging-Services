package com.nzion.view.component;

import static com.nzion.domain.Roles.getRoleValue;
import static com.nzion.domain.Roles.hasRole;

import java.io.IOException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Window;

/**
 * @author Sandeep Prusty
 * Aug 28, 2010
 */
public class DetachEventPostingWindow extends Window {

	private String roles;

	public String getRoles() {
	return roles;
	}

	public void setRoles(String roles) throws IOException {
	this.roles = roles;
	if (hasRole(getRoleValue(roles))) return;
	addEventListener("onCreate", new EventListener() {
		@Override
		public void onEvent(Event event) throws Exception {
		getChildren().clear();
		Executions.createComponents("/unauthorizedAccess.zul", DetachEventPostingWindow.this, null);
		}
	});
	}

	@Override
	public void detach() {
		clear();
	Events.postEvent("onDetach", this, null);
	super.detach();
	}

	public void onCancel(Event event) {
		clear();
	if ("modal".equalsIgnoreCase(getMode())) super.detach();
	}
	
	private void clear(){
		Component parent =  DetachEventPostingWindow.this.getParent();
		 if(parent!=null)
			 parent.removeAttribute("windowOpened");
		}

	private static final long serialVersionUID = 1L;
	
	
	
	
	
		
		
		
		

		
}