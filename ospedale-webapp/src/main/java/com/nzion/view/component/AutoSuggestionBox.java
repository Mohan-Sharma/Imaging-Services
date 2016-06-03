/**
 * @author shwetha
 * Dec 1, 2010 
 */
package com.nzion.view.component;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;

import com.nzion.repository.ComponentRepository;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilValidator;

public class AutoSuggestionBox extends LookupBox {

	private String[] searchFields;

	private ComponentRepository componentRepository = (ComponentRepository) Infrastructure.getSpringBean("componentRepository");

	public AutoSuggestionBox() {
	setButtonVisible(false);
	setAutodrop(true);
	setHideListHeader(true);
	setHidePagination(true);
	addEventListener("onChanging", new OnChangingEventListener());
	}

	@Override
	protected void buildSearchBox(Bandpopup bandpopup) {
	Hbox hbox = new Hbox();
	hbox.setParent(bandpopup);
	Div div = new Div();
	div.setParent(bandpopup);
	}

	@Override
	public void setSearchcolumns(String sc) {
	super.setSearchcolumns(sc);
	searchFields = new String[searchcolumns.length];
	for (int i = 0; i < searchcolumns.length; i++)
		searchFields[i] = searchcolumns[i][0];
	}

	private class OnChangingEventListener implements EventListener {

		public void onEvent(Event event) throws Exception {
		String value = ((InputEvent) event).getValue();
		if(UtilValidator.isEmpty(value) || value.length() < 2)
			return;
		List<?> toBeDisplayed = componentRepository.searchEntities(value, entity, searchFields);
		buildDisplayBox(toBeDisplayed);
		}
	}

	private static final long serialVersionUID = 1L;
}