package com.nzion.zkoss.ext;

import java.util.Arrays;
import java.util.Iterator;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlMacroComponent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Space;
import org.zkoss.zul.impl.InputElement;

import com.nzion.domain.Condition;
import com.nzion.domain.SearchOperator;

public class Conditionbox
		extends HtmlMacroComponent {

	private String fieldLabel;
	private String operator;
	private Object value;
	private String opType;
	private Condition condition;
	private String fieldExpr;

	public Conditionbox() {
	super();
	}

	public String getFieldLabel() {
	return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
	this.fieldLabel = fieldLabel;
	}

	private Component getComponentOfType(Class componentClass) {
	for (Iterator iter = this.getChildren().iterator(); iter.hasNext();) {
		Component comp = (Component) iter.next();
		if (componentClass.isAssignableFrom(comp.getClass())) {
			return comp;
		}
	}
	return null;
	}

	public String getOperator() {
	return operator;
	}

	public void setOperator(String operator) {
	this.operator = operator;
	}

	public Object getValue() {
	return value;
	}

	public void setValue(Object value) {
	this.value = value;
	}

	public String getOpType() {
	return opType;
	}

	public void setOpType(String opType) {
	this.opType = opType;
	}

	public Condition getCondition() {
	return condition;
	}

	public void setCondition(Condition condition) {
	this.condition = condition;
	}

	public String getFieldExpr() {
	return fieldExpr;
	}

	public void setFieldExpr(String fieldExpr) {
	this.fieldExpr = fieldExpr;
	}

	@Override
	public void afterCompose() {
	super.afterCompose();
	Label l = (Label)getComponentOfType(Label.class);
	l.setValue(getFieldLabel());
	Combobox combo = (Combobox) getComponentOfType(Combobox.class);
	Space space = (Space) getComponentOfType(Space.class);
	InputElement input = null;
	if ("number".equalsIgnoreCase(opType)) {
		combo.setModel(new BindingListModelList(Arrays.asList(SearchOperator.getNumericOperators()), false));
		input = new Doublebox();
	} else {
		combo.setModel(new BindingListModelList(Arrays.asList(SearchOperator.getStringOperators()), false));
		input = new Doublebox();
	}
	space.getParent().appendChild(input);
	space.detach();
	AnnotateDataBinder binder = new AnnotateDataBinder(this);
	this.setAttribute("condition", condition);
	binder.addBinding(combo, "selectedItem", "condition.searchOperator");
	binder.addBinding(input, "value", "condition.rhs");
	condition.setLhs(fieldExpr);
	Label label = (Label) getComponentOfType(Label.class);
	label.setValue(fieldLabel);
	binder.loadAll();
	}

}
