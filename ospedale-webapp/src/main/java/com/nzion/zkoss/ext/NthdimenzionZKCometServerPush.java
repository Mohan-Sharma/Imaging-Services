package com.nzion.zkoss.ext;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

/**
 * @author Sandeep Prusty
 * Apr 19, 2011
 */
public class NthdimenzionZKCometServerPush {
	
	private static final String PUSH_ENABLED_DESKTOPS = "com.nzion.pushenabled.desktop";
	
	public void start(Desktop arg0) {
	startNew(arg0);
	//super.start(arg0);
	}

	private void startNew(Desktop desktopArg){
	Session session = Sessions.getCurrent();
	List<Desktop> desktops = (List<Desktop>) session.getAttribute(PUSH_ENABLED_DESKTOPS);
	if(desktops == null){
		desktops = new ArrayList<Desktop>();
		session.setAttribute(PUSH_ENABLED_DESKTOPS, desktops);
	}
	final int maxcnt = desktopArg.getWebApp().getConfiguration().getSessionMaxPushes();
	if(desktops.size() + 1 >= maxcnt){
		for(Desktop desktop : desktops){
			if(desktop.isServerPushEnabled()){
				desktop.enableServerPush(false);
			}
		}
		desktops.clear();
	}
	desktops.add(desktopArg);
	return;
	}
}