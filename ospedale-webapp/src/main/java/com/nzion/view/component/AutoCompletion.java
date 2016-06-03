package com.nzion.view.component;

import java.util.List;

import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.sys.ContentRenderer;
import org.zkoss.zul.impl.XulElement;

import com.nzion.util.UtilValidator;

public class AutoCompletion extends XulElement {

	private List<AutoCompletionItem> _model;
	private AutoCompletionService autoCompletionService;
	private String value;
	private Object selectedItem;

	private List<AutoCompletionItem> getModel() {
	return _model;
	}

	private void setModel(List<AutoCompletionItem> model) {
	this._model = model;
	this.smartUpdate("model", _model);
	}

	@Override
	protected void renderProperties(ContentRenderer renderer) throws java.io.IOException {
	super.renderProperties(renderer);
	render(renderer, "model", _model);
	}

	@Override
	public void service(org.zkoss.zk.au.AuRequest request, boolean everError) {
	final String cmd = request.getCommand();
	if (cmd.equals("onSearch")) {
		String searchTerm = (String) request.getData().get("searchTerm");
		setModel(getAutoCompletionService().search(searchTerm));
	} else
		if (cmd.equals(Events.ON_SELECT)) {
			SelectEvent evt = SelectEvent.getSelectEvent(request);
			JSONObject _value = (JSONObject) request.getData().get("selectedItem");
			if (_value != null) {
				AutoCompletionItem _selItem = new AutoCompletionItem(new Long((String) _value.get("id")), (String) _value.get("desc"));
				setSelectedItem(getAutoCompletionService().convertToObject(_selItem));
			}
			Events.postEvent(evt);
		}
	}

	public AutoCompletionService getAutoCompletionService() {
	return autoCompletionService;
	}

	public void setAutoCompletionService(AutoCompletionService autoCompletionService) {
	this.autoCompletionService = autoCompletionService;
	}

	public String getValue() {
	return value;
	}

	public void setValue(String value) {
	this.value = value;
	this.smartUpdate("value", value);
	}

	public Object getSelectedItem() {
	return selectedItem;
	}

	public void setSelectedItem(Object selectedItem) {
	if(UtilValidator.isEmpty(selectedItem)) {
		throw new WrongValueException(this, "No User found.");
	}
	this.selectedItem = selectedItem;
	}

}
