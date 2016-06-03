package com.nzion.view.component;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.BindingListModelSet;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Span;

public class ImageConverter implements TypeConverter {
	public Object coerceToBean(java.lang.Object val, org.zkoss.zk.ui.Component comp) {
	return null;
	}

	public Object coerceToUi(java.lang.Object val, final org.zkoss.zk.ui.Component comp) {
	final Listcell lc = ((Listcell)comp);
	Span span = new Span();
	if (val != null) {
		boolean confidential = (Boolean) val;
		if (confidential) {
			span.setSclass("sectionLock");
		}
	} else {
		span.addEventListener("onClick", new EventListener() {
			public void onEvent(Event event) throws Exception {
			Listitem li = ((Listitem) comp.getParent());
			Listbox lbox = ((Listbox) comp.getParent().getParent());
			((BindingListModelSet)lbox.getModel()).remove(li.getValue());
			Events.postEvent("onReload",lbox,null);
			}
		});
	}
	span.setParent(comp);
	lc.getParent().setWidgetListener("onMouseOver","showDelRow('"+span.getUuid()+"')");
	lc.getParent().setWidgetListener("onMouseOut","hideDelRow('"+span.getUuid()+"')");
	return null;
	}

}
