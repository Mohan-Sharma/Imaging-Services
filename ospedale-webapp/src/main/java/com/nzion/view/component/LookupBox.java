package com.nzion.view.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import com.nzion.repository.ComponentRepository;
import com.nzion.util.Constants;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDisplay;
import com.nzion.util.UtilReflection;
import com.nzion.util.UtilValidator;

/**
 * @author Sandeep Prusty
 * May 12, 2010
 */
public class LookupBox extends Bandbox {
	
	private static final long serialVersionUID = 1L;

	private Map<String, Object> data;
	
	protected String[][] searchcolumns;
	
	private String[][] displaycolumns;
	
	private boolean useLabels;
	
	protected Class<?> entity;
	
	private String requiredcolumn;
	
	private boolean built;
	
	private Object result;
	
	private Evaluator evaluator = DEFAULTEVALUATOR;
	
	private boolean hidePagination;
	
	private boolean hideListHeader;
	
	public void setHidePagination(boolean hidePagination) {
	this.hidePagination = hidePagination;
	}

	public void setHideListHeader(boolean hideListHeader) {
	this.hideListHeader = hideListHeader;
	}

	public LookupBox() {
	data = new HashMap<String, Object>();
	}
	
	public LookupBox(String requiredcolumn,String entity,String searchcolumns,String displaycolumns) {
	this();
	setRequiredcolumn(requiredcolumn);
	setEntity(entity);
	setSearchcolumns(searchcolumns);
	setDisplaycolumns(displaycolumns);
	}

	private void buildComponents() {
	if(built)
		return;
	if((entity == null) ||( searchcolumns == null && displaycolumns == null))
		return;
	Bandpopup bandpopup = new Bandpopup();
	bandpopup.setParent(this);
	buildSearchBox(bandpopup);
	built = true;
	}

	protected void buildSearchBox(Bandpopup bandpopup) {
	Hbox hbox = new Hbox();
	hbox.setParent(bandpopup);
	Div div = new Div();
	div.setParent(bandpopup);
	if(UtilValidator.isEmpty(searchcolumns))
		return;
	DataBinder binder = new AnnotateDataBinder(bandpopup);
	binder.bindBean("data", data);
	int iCount=0;
	for(String[] column : searchcolumns){
		Vbox vbox = new Vbox();
		vbox.setParent(hbox);
		Label label = new Label(useLabels ? column[1] : UtilDisplay.camelcaseToUiString(column[0]));
		Textbox textbox = new Textbox();
		label.setParent(vbox);
		textbox.setParent(vbox);
		binder.addBinding(textbox, "value", "data."+column[0]);
		iCount++;
	}
	Vbox searchVbox = new Vbox();
	searchVbox.setParent(hbox);
	new Space().setParent(searchVbox);
	Button button = new Button("Search");
	button.setParent(searchVbox);
	button.addEventListener("onClick", new SearchButtonClickListener());
	binder.loadAll();
	}
	
	public String getRequiredcolumn() {
	return requiredcolumn;
	}

	protected void buildDisplayBox(List<?> toBeDisplayed) {
	Component div = (Component)getFirstChild().getChildren().get(1);
	div.getChildren().clear();
	Listbox listbox = new Listbox();
	listbox.setParent(div);
	listbox.addEventListener("onSelect", new OnSelectListener());
	if(!hidePagination){
		Paging paging = new Paging();
		paging.setPageSize(Constants.PAGE_SIZE);
		paging.setPageIncrement(1);
		paging.setParent(div);
		listbox.setPaginal(paging);
		listbox.setMold("paging");
	}
	if(!hideListHeader){
		Listhead listhead = new Listhead();
		listhead.setParent(listbox);
		for(String[] column : displaycolumns){
			Listheader listheader = new Listheader(useLabels ? column[1] : UtilDisplay.camelcaseToUiString(column[0]));
			listheader.setParent(listhead);
		}
	}
	for(Object object : toBeDisplayed){
		Listitem listitem = new Listitem();
		listitem.setParent(listbox);
		listitem.setAttribute("object", object);
		for(String[] column : displaycolumns){
			Listcell listcell = new Listcell();
			listcell.setParent(listitem);
			Object value = UtilReflection.getFieldValue(object, column[0]);
			Label label = new Label(value == null ? "" : value.toString());
			label.setParent(listcell);
		}
	}
	}

	public void setEntity(String entity){
	try {
		this.entity = Class.forName(entity);
	} catch (ClassNotFoundException e) {
		throw new RuntimeException(e);
	}
	buildComponents();
	}
	
	public void setSearchcolumns(String sc) {
	String[] searchColumns = sc.split(",");
	searchcolumns = buildArray(this.searchcolumns, searchColumns.length);
	int i = 0;
	for(String s : searchColumns){
		this.searchcolumns[i++][0] = s.trim();
	}
	buildComponents();
	}

	public void setEvaluator(Evaluator evaluator) {
	this.evaluator = evaluator;
	}

	public void setDisplaycolumns(String dc) {
	String[] displayColumns = dc.split(",");
	displaycolumns = buildArray(this.displaycolumns, displayColumns.length);
	int i = 0;
	for(String s : displayColumns){
		this.displaycolumns[i++][0] = s.trim();
	}
	buildComponents();
	}
	
	public void setRequiredcolumn(String requiredcolumn) {
	this.requiredcolumn = requiredcolumn;
	}

	private class OnSelectListener implements EventListener{

		public void onEvent(Event event) throws Exception {
		Listbox listbox = (Listbox) event.getTarget();
		Object value = listbox.getSelectedItem().getAttribute("object");
		result = value;
		setValue(UtilReflection.getFieldValue(value, requiredcolumn).toString());
		listbox.detach();
		close();
		Events.postEvent("onChange",LookupBox.this, null);
		Events.postEvent("onLookedUp",LookupBox.this, null);
		}
	}

	private class SearchButtonClickListener implements EventListener{

		public void onEvent(Event event) throws Exception {
		if(UtilValidator.isEmpty(data))
			throw new WrongValueException(LookupBox.this, "Atleast one search criteria is mandatory.");
		List<?> result = evaluator.doLookup(entity, data);
		buildDisplayBox(result);
		}
	}

	public Object getResult() {
	return result;
	}

	public void setResult(Object result) {
	this.result = result;
	}
	
	public void setSearchcolumnlabel(String searchcolumnlabel){
	String[] temp = searchcolumnlabel.split(",");
	searchcolumns = buildArray(searchcolumns, temp.length);
	int i = 0;
	for(String s : temp){
		this.searchcolumns[i++][1] = s.trim();
	}
	useLabels = true;
	}
	
	public void setDisplaycolumnlabel(String displaycolumnlabel){
	String[] temp = displaycolumnlabel.split(",");
	displaycolumns = buildArray(displaycolumns, temp.length);
	int i = 0;
	for(String s : temp){
		this.displaycolumns[i++][1] = s.trim();
	}
	useLabels = true;
	}
	
	private String[][] buildArray(String[][] arr, int length){
	if(arr != null)
		return arr;
	arr = new String[length][length];
	for(int i = 0 ; i < length ; ++i)
		arr[i] = new String[2];
	return arr;
	}
	
	public static interface Evaluator {
		List<? extends Object> doLookup(Class<?> klass, Map<String, Object> data);
	}
	
	private static final Evaluator DEFAULTEVALUATOR = new Evaluator() {
		public List<? extends Object> doLookup(Class<?> klass, Map<String, Object> data) {
			ComponentRepository componentRepository=(ComponentRepository)Infrastructure.getSpringBean("componentRepository");
			return componentRepository.findByParamsMap(klass, data);
		}
	};
}