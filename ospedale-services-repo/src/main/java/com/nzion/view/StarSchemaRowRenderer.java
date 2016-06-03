package com.nzion.view;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

public final class StarSchemaRowRenderer implements RowRenderer {

    private String[] columns;

	private Class<?> doubleClickRowListenerClass;

	public StarSchemaRowRenderer() {}

	public StarSchemaRowRenderer(String[] columns, Class<?> listenerClass) {
	this.columns = columns;
	this.doubleClickRowListenerClass = listenerClass;
	}

	public void render(Row row, Object data,int index) throws Exception {
	if (data == null) return;
	com.nzion.domain.olap.StarSchemaObject dataObj = (com.nzion.domain.olap.StarSchemaObject) data;
	Map valueMap = dataObj.getFields();
	if (valueMap != null) {
		for (String colName : columns) {
			Label l = new Label();
			Object val = valueMap.get(colName);
			if (colName.equalsIgnoreCase("accountNumber")) {
				val = BeanUtils.getProperty(dataObj, colName);
			}
			l.setValue(val != null ? val.toString() : "");
			l.setParent(row);
		}
		if (doubleClickRowListenerClass != null)
			row.addEventListener("onDoubleClick", (EventListener) doubleClickRowListenerClass.getConstructor(new Class[] { Object.class }).newInstance(new Object[] { data }));
	}
	}
}