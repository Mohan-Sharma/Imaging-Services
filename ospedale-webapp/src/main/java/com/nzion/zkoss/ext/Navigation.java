package com.nzion.zkoss.ext;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Window;

import com.nzion.util.Infrastructure;

public class Navigation {

	public static AbstractCachingViewResolver resolver = Infrastructure.getSpringBean("ecosmosViewResolver");

	public static void navigate(String viewName, Map<?, ?> arguments) {
	navigate(viewName,arguments,(String)null);
	}

	public static void  navigate(String viewName, Map<?, ?> arguments,Component container) {
	resolver.navigate(viewName, arguments,container);
	}


	public static void  navigate(String viewName, Map<?, ?> arguments,Component container,boolean append) {
	resolver.navigate(viewName, arguments,container,append);
	}

	
	public static void navigate(String viewName, String defaultViewName, Map<?, ?> arguments,Component container) {
	resolver.navigate(viewName, defaultViewName, arguments,container);
	}
	
	public static boolean viewExists(String viewName){
	return resolver.viewExists(viewName);
	}

	public static void navigate(String viewName, Map<?, ?> arguments,String componentName) {
	resolver.navigate(viewName, arguments,componentName);
	}

	public static Window navigateToModalWindow(String url, Map<?, ?> arguments){
	Window window = url.contains(".zul") ? resolver.getWindowForURI(url,arguments) : resolver.getWindowForViewName(url,arguments);
	try {
		window.doModal();
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
	return window;
	}
	
	 public static Window openModalWindowWithParent(String viewName, Map<?, ?> arguments,org.zkoss.zk.ui.Component parent){
		 if(parent.getAttribute("windowOpened")==null){
		 Window window = resolver.getWindowForViewName(viewName, arguments);
		 if(parent!=null){
		 window.setParent(parent);
		 parent.setAttribute("windowOpened", new Object());
		 }
		 window.doModal();
		 return window;
		 }
		 return null;
		 }

}