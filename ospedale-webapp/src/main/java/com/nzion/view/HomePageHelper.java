package com.nzion.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.Panel;

import com.nzion.domain.Roles;

/**
 * @author Sandeep Prusty
 * Dec 13, 2010
 */
public class HomePageHelper {
	
	final static Map<Long, String> roleHomePageMap = new HashMap<Long, String>();
	static {
		roleHomePageMap.put(Roles.SUPER_ADMIN, "/practice/practicelist.zul");
		roleHomePageMap.put(Roles.ADMIN, "/practice/practiceView.zul");
		roleHomePageMap.put(Roles.PROVIDER, "/dashboards/providerDashboard.zul");
		roleHomePageMap.put(Roles.NURSE, "/dashboards/nurseDashboard.zul");
		roleHomePageMap.put(Roles.RECEPTION, "/dashboards/frontDeskDashBoard.zul");
		roleHomePageMap.put(Roles.TECHNICIAN, "/dashboards/labdashboard.zul");
		roleHomePageMap.put(Roles.PHLEBOTOMIST, "/dashboards/labdashboard.zul");
		//roleHomePageMap.put(Roles.ADJUSTER, "/patient/patientList.zul");
		roleHomePageMap.put(Roles.CASE_MANAGER, "/patient/patientList.zul");
		roleHomePageMap.put(Roles.MEDICAL_ASSISTANT, "/dashboards/providerDashboard.zul");
		roleHomePageMap.put(Roles.PATIENT, "/patient/patientDashboard.zul");
		roleHomePageMap.put(Roles.EMERGENCY_ACCESS, "/patient/emergencyDashboard.zul");
		roleHomePageMap.put(Roles.BILLING, "/billing/billingDashboard.zul");
		roleHomePageMap.put(Roles.HOUSE_KEEPING, "/dashboards/houseKeepingDashboard.zul");
		roleHomePageMap.put(Roles.ORDER_MANAGEMENT, "/dashboards/orderManagementDashboard.zul");
		roleHomePageMap.put(Roles.STORE_MANAGEMENT, "/dashboards/storeManagementDashboard.zul");
	}
	
	public static String getHomePageForLoggedInUser(){
	Long role = (Long) Sessions.getCurrent().getAttribute("_role");
	return roleHomePageMap.get(role);
	}
	
	public static void maximize(Panel panel){
	if("maximized".equals(panel.getFirstChild().getAttribute("mode"))){
		togglePanel("normal","normal","275px","275px","66%","33%",panel.getParent().getParent());
		List<Component> portalChildrenList = panel.getParent().getParent().getChildren(); 
		for(Component component : portalChildrenList)
			Events.postEvent("onRestore",((Panel)component.getFirstChild()).getPanelchildren().getFirstChild().getFirstChild(), null);
		return;
	}
	Component portalchildrenOne = panel.getParent().getParent().getFirstChild();
    Panel bigInclude = (Panel)portalchildrenOne.getFirstChild();
    bigInclude.setParent(panel.getParent());
    panel.setParent(portalchildrenOne);
    togglePanel("maximized","minimized","500px","24px","85%","15%",panel.getParent().getParent());
	Events.postEvent("onMaximize",panel.getPanelchildren().getFirstChild().getFirstChild(), null);
	}

	@SuppressWarnings("unchecked")
	public static void togglePanel(String childOneMode,String otherChildrenMode,String childOneHeight,String otherChildrenHeight,String childOneWidth,String otherChildWidth, Component main){
	List<Component> compList = main.getChildren();
	 changePanelSize((Panel)compList.get(0).getFirstChild(), main.getFirstChild(), childOneMode,childOneHeight, childOneWidth, ("minimized".equals(childOneMode)?"/images/minimize.png":"/images/maximize.png") );
	 for(int i = 1 ; i < compList.size() ; ++i){
		changePanelSize((Panel)compList.get(i).getFirstChild(), compList.get(i), otherChildrenMode,otherChildrenHeight,otherChildWidth,("minimized".equals(otherChildrenMode)?"/images/minimize.png":"/images/maximize.png") );
	 }
	}
	
	public static void changePanelSize(Panel panel,Component portalchildren, String mode, String panelHeight, String portalWidth, String imgurl){
	panel.setHeight(panelHeight);
	((HtmlBasedComponent)portalchildren).setWidth(portalWidth);
	panel.getFirstChild().setAttribute("mode", mode);
	// Visibility of the refresh button..
	panel.getFirstChild().getLastChild().setVisible(!"minimized".equalsIgnoreCase(mode));
	((A)panel.getFirstChild().getFirstChild()).setImage(imgurl);
	}
}