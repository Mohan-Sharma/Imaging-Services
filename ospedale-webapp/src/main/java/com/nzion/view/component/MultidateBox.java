package com.nzion.view.component;

import static com.nzion.util.UtilDateTime.DAYSINSHORT;
import static com.nzion.util.UtilDateTime.MONTH_DATE_FORMATTER;
import static com.nzion.util.UtilDateTime.addDaysToDate;
import static com.nzion.util.UtilDateTime.format;
import static com.nzion.util.UtilDateTime.getIntervalInDays;
import static com.nzion.util.UtilDateTime.getWeekEnd;
import static com.nzion.util.UtilDateTime.getWeekStart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Box;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

/**
 * @author Sandeep Prusty
 * Jun 22, 2010
 */
public class MultidateBox extends Box {

	private Date start;

	private Date end;

	private List<Checkbox> checkboxes = new ArrayList<Checkbox>();
	
	private Listbox listbox;
	
	private Set<Date> choosenDates = new HashSet<Date>();
	
	private Checkbox allCheckbox = new Checkbox();

	public Set<Date> getChoosenDates() {
	return choosenDates;
	}

	public void setChoosenDates(Set<Date> choosenDates) {
	this.choosenDates = choosenDates;
	}

	public void setStart(Date start) {
	this.start = start;
	build();
	}

	public void setEnd(Date end) {
	this.end = end;
	build();
	}

	private void build(){
	if(start == null || end == null || start.after(end))
		return;
	listbox = buildHeader();;
	Calendar calendar = Calendar.getInstance();
	Date firstWeekStart = getWeekStart(start);
	Date lastWeekEnd = getWeekEnd(end);
	int noOfDays = getIntervalInDays(firstWeekStart, lastWeekEnd);
	calendar.setTime(firstWeekStart);
	Date current = firstWeekStart;
	for(int i = 0 ; i < ((noOfDays / 7) + 1) ; ++i){
		Listitem item = new Listitem();
		item.setParent(listbox);
		Listcell controllerCell = new Listcell();
		controllerCell.setParent(item);
		Checkbox controllerCheckbox = new Checkbox();
		controllerCheckbox.setParent(controllerCell);
		controllerCheckbox.addEventListener("onCheck", new RowSelectListener());
		for(int j = 0 ; j < 7 ; ++j){
			Listcell cell = new Listcell();
			cell.setParent(item);
			if(!current.before(start) && !current.after(end)){
				Checkbox checkbox = new Checkbox();
				checkbox.setParent(cell);
				checkbox.setLabel(format(current, MONTH_DATE_FORMATTER));
				checkbox.setAttribute("date", current);
				checkboxes.add(checkbox);
				checkbox.addEventListener("onCheck", new IndividualSelectionListener());
			}
			current = addDaysToDate(current, 1);
		}
	}
	}
	
	private Listbox buildHeader() {
	getChildren().clear();
	Listbox listbox = new Listbox();
	listbox.setParent(this);
	Listhead head = new Listhead();
	head.setParent(listbox);
	Listheader allHeader = new Listheader();
	allHeader.setParent(head);
	allHeader.setWidth("50px");
	allCheckbox.setParent(allHeader);
	allCheckbox.setLabel("All");
	allCheckbox.addEventListener("onCheck", new AllSelectListener());
	for(int i = 0 ; i < 7 ; ++i){
		Listheader header = new Listheader();
		header.setParent(head);
		Checkbox checkbox = new Checkbox();
		checkbox.setParent(header);
		checkbox.setLabel(DAYSINSHORT[i]);
		checkbox.addEventListener("onCheck", new ColumnSelectListener());
	}
	return listbox;
	}
	
	private void changeCheckboxSelection(Checkbox checkbox, boolean checked){
	checkbox.setChecked(checked);
	checkboxSelectionChanged(checkbox);
	}
	
	private void checkboxSelectionChanged(Checkbox checkbox){
	if(checkbox.isChecked())
		choosenDates.add((Date)checkbox.getAttribute("date"));
	else
		choosenDates.remove((Date)checkbox.getAttribute("date"));
	checkAllSelection();
	checkColumnSelection(checkbox);
	checkRowSelection(checkbox);
	}

	private void checkAllSelection() {
	allCheckbox.setChecked(choosenDates.size() == checkboxes.size());
	}
	
	private void checkColumnSelection(Checkbox checkbox) {
	Listcell selectedCell = (Listcell) checkbox.getParent();
	int columnIndex = selectedCell.getParent().getChildren().indexOf(selectedCell);
	List<Listitem> items =  listbox.getItems();
	for(Listitem item : items){
		Listcell cell = (Listcell)item.getChildren().get(columnIndex);
		if(cell.getFirstChild() != null && !((Checkbox)cell.getFirstChild()).isChecked()){
			setValueForColumnCheckbox(false, columnIndex);
			return;
		}
	}
	setValueForColumnCheckbox(true, columnIndex);
	}
	
	private void checkRowSelection(Checkbox checkbox) {
	Listitem row = (Listitem)checkbox.getParent().getParent();
	for(int i = 1 ; i < row.getChildren().size() ; ++i){
		Listcell cell = (Listcell)(row.getChildren().get(i));
		if(cell.getFirstChild() != null && !((Checkbox)cell.getFirstChild()).isChecked()){
			setValueForRowCheckbox(false, row);
			return;
		}
	}
	setValueForRowCheckbox(true, row);
	}

	private void setValueForColumnCheckbox(boolean bulian, int columnIndex){
	Listheader header = (Listheader)listbox.getFirstChild().getChildren().get(columnIndex);
	((Checkbox)header.getFirstChild()).setChecked(bulian);
	}
	
	private void setValueForRowCheckbox(boolean bulian, Listitem row){
	((Checkbox)(row.getFirstChild().getFirstChild())).setChecked(bulian);
	}

	private class AllSelectListener implements EventListener {

		public void onEvent(Event event) throws Exception {
			boolean bulean = ((Checkbox) event.getTarget()).isChecked();
			Listhead head = listbox.getListhead();
			for (Object object : head.getChildren())
				((Checkbox) ((Listheader) object).getFirstChild()).setChecked(bulean);
			for (Checkbox checkbox : checkboxes){
				checkbox.setChecked(bulean);
				choosenDates.add((Date)checkbox.getAttribute("date"));
			}
			List<Listitem> items = listbox.getItems();
			for (Listitem item : items)
				((Checkbox) item.getFirstChild().getFirstChild()).setChecked(bulean);
			if(!bulean)
				choosenDates.clear();
		}
	}
	
	private class ColumnSelectListener implements EventListener {

		public void onEvent(Event event) throws Exception {
		Checkbox selectedCheckbox = (Checkbox)event.getTarget();
		Listheader selectedCell = (Listheader) selectedCheckbox.getParent();
		int columnIndex = selectedCell.getParent().getChildren().indexOf(selectedCell);
		boolean bulean = selectedCheckbox.isChecked();
		List<Listitem> items =  listbox.getItems();
		for(Listitem item : items){
			Listcell cell = (Listcell)item.getChildren().get(columnIndex);
			if(cell.getFirstChild() != null)
				changeCheckboxSelection((Checkbox)cell.getFirstChild(), bulean);
		}
		}
	}
	
	private class RowSelectListener implements EventListener {

		public void onEvent(Event event) throws Exception {
		Checkbox selectedCheckbox = (Checkbox)event.getTarget();
		boolean bulean = selectedCheckbox.isChecked();
		Listitem row = (Listitem)selectedCheckbox.getParent().getParent();
		for(int i = 1 ; i < row.getChildren().size() ; ++i){
			Listcell cell = (Listcell)(row.getChildren().get(i));
			if(cell.getFirstChild() != null)
				changeCheckboxSelection((Checkbox)cell.getFirstChild(), bulean);
		}
		} 
	}
	
	private class IndividualSelectionListener implements EventListener {

		public void onEvent(Event event) throws Exception {
		Checkbox checkbox = (Checkbox)event.getTarget();
		checkboxSelectionChanged(checkbox);
		}
	}
	
	private static final long serialVersionUID = 1L;
}