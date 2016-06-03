package com.nzion.view.component;

import static com.nzion.util.UtilDisplay.camelcaseToUiString;
import static com.nzion.util.UtilReflection.getFieldValue;
import static com.nzion.util.UtilValidator.isEmpty;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Vbox;

/**
 * @author Sandeep Prusty
 * May 13, 2010
 */

@SuppressWarnings("unchecked")
public class ShuttleBox extends Hbox{

	private List source;
	
	private List target;
	
	private Listbox sourceBox = new Listbox();
	
	private Listbox targetBox = new Listbox();
	
	private Button addAllButton = new Button("Add All");

	private Button addButton = new Button("Add");

	private Button removeButton = new Button("Remove");

	private Button removeAllButton = new Button("Remove All");

	private Button topButton = new Button("Top");

	private Button upButton = new Button("Up");

	private Button downButton = new Button("Down");
	
	private Button bottomButton = new Button("Bottom");

	private String boxwidth = "200px";
	
	private String buttonwidth = "100px";
	
	private String displayfield;
	
	private boolean sourceFed, targetFed; 
	
	public ShuttleBox() {
	buildComponents();
	aggregate();
	addListeners();
	}

	private void buildComponents() {
	sourceBox.setMultiple(true);
	sourceBox.setWidth(boxwidth);
	sourceBox.setHeight("300px");
	targetBox.setMultiple(true);
	targetBox.setWidth(boxwidth);
	targetBox.setHeight("300px");
	addButton.setWidth(buttonwidth);
	addAllButton.setWidth(buttonwidth);
	removeButton.setWidth(buttonwidth);
	removeAllButton.setWidth(buttonwidth);
	upButton.setWidth(buttonwidth);
	topButton.setWidth(buttonwidth);
	downButton.setWidth(buttonwidth);
	bottomButton.setWidth(buttonwidth);
	sourceBox.setStyle("border-top: solid 1px #999999;");
	targetBox.setStyle("border-top: solid 1px #999999;");
	}

	private void aggregate() {
	sourceBox.setParent(this);
	Vbox middleBox = new Vbox();
	middleBox.setParent(this);
	middleBox.setAlign("center");
	middleBox.setPack("center");
	middleBox.setHeight("100%");
	targetBox.setParent(this);
	addAllButton.setParent(middleBox);
	addButton.setParent(middleBox);
	removeButton.setParent(middleBox);
	removeAllButton.setParent(middleBox);
	Vbox rightBox = new Vbox();
	rightBox.setParent(this);
	rightBox.setAlign("center");
	rightBox.setPack("center");
	rightBox.setHeight("100%");
	topButton.setParent(rightBox);
	upButton.setParent(rightBox);
	downButton.setParent(rightBox);
	bottomButton.setParent(rightBox);
	}

	private void addListeners(){
	
	addAllButton.addEventListener("onClick", new EventListener() {
		public void onEvent(Event event) throws Exception {
		List<Component> allSourceItems = new ArrayList<Component>(sourceBox.getChildren());
		for (Component item : allSourceItems) {
			item.detach();
			item.setParent(targetBox);
			targetBox.appendChild(item);
		}
		target.addAll(source);
		source.clear();
		}
	});

	addButton.addEventListener("onClick", new EventListener() {
		public void onEvent(Event event) throws Exception {
		List<Listitem> selectedItems = new ArrayList<Listitem>(sourceBox.getSelectedItems());
		for(Listitem item : selectedItems){
			item.detach();
			item.setParent(targetBox);
			targetBox.appendChild(item);
			Object element = item.getAttribute("element");
			source.remove(element);
			target.add(element);
		}
		}
	});
	
	removeButton.addEventListener("onClick", new EventListener() {
		public void onEvent(Event event) throws Exception {
		List<Listitem> selectedItems = new ArrayList<Listitem>(targetBox.getSelectedItems());
		for(Listitem item : selectedItems){
			item.detach();
			item.setParent(sourceBox);
			sourceBox.appendChild(item);
			Object element = item.getAttribute("element");
			target.remove(element);
			source.add(element);
		}
		}
	});
	
	removeAllButton.addEventListener("onClick", new EventListener() {
		public void onEvent(Event event) throws Exception {
		List<Component> allTargetItems = new ArrayList<Component>(targetBox.getChildren());
		for (Component item : allTargetItems) {
			item.detach();
			item.setParent(targetBox);
			sourceBox.appendChild(item);
		}
		source.addAll(target);
		target.clear();
		}
	});
	
	topButton.addEventListener("onClick", new EventListener() {
		public void onEvent(Event arg0) throws Exception {
		Set<Listitem> selectedItems = targetBox.getSelectedItems();
		Listitem firstItemInTargetBox = (Listitem)targetBox.getFirstChild();
		int index = 0;
		for(Listitem item : selectedItems){
			targetBox.insertBefore(item, firstItemInTargetBox);
			target.set(index++, item.getAttribute("element"));
		}
		}
	});
	
	upButton.addEventListener("onClick", new EventListener() {
		public void onEvent(Event event) throws Exception {
		List<Listitem> selectedItems = new ArrayList<Listitem>(targetBox.getSelectedItems());
		for(Listitem item : selectedItems){
			int currentIndex = item.getIndex();
			if(currentIndex == 0)
				continue;
			Listitem itemBeforeTheCurrentItem = targetBox.getItemAtIndex(currentIndex - 1);
			targetBox.removeChild(item);
			targetBox.insertBefore(item, itemBeforeTheCurrentItem);
		}
		}
	});
	
	downButton.addEventListener("onClick", new EventListener() {
		public void onEvent(Event event) throws Exception {
		List<Listitem> selectedItems = new ArrayList<Listitem>(targetBox.getSelectedItems());
		for(Listitem item : selectedItems){
			int currentIndex = item.getIndex();
			if(currentIndex == (targetBox.getChildren().size() - 1))
				continue;
			targetBox.removeChild(item);
			if(currentIndex == (targetBox.getChildren().size() - 1)){
				targetBox.appendChild(item);
				continue;
			}
			Listitem itemTwoStepsAfterTheCurrentItem = targetBox.getItemAtIndex(currentIndex + 1);
			targetBox.insertBefore(item, itemTwoStepsAfterTheCurrentItem);
		}
		}
	});
	
	bottomButton.addEventListener("onClick", new EventListener() {
		public void onEvent(Event arg0) throws Exception {
		Listitem[] selectedItems = new Listitem[targetBox.getSelectedItems().size()];
		targetBox.getSelectedItems().toArray(selectedItems);
		for (Listitem item : selectedItems) {
			targetBox.removeChild(item);
			targetBox.appendChild(item);
			target.remove(item.getAttribute("element"));
			target.add(item.getAttribute("element"));
		}
		}
	});
	}

	private void sanitizeAndCreateListItems() {
	if(!sourceFed || !targetFed)
		return;
	if(source != null && target != null)
		source.removeAll(target);
	createListItems(source, sourceBox);
	createListItems(target, targetBox);
	}
	
	private void createListItems(List list, Listbox listbox){
	listbox.getChildren().clear();
	if(isEmpty(list))
		return;
	if(isEmpty(displayfield)){
		for(Object o : list){
			Listitem item = new Listitem(camelcaseToUiString(o.toString()));
			item.setParent(listbox);
			item.setAttribute("element", o);
		}
		return;
	}
	for(Object o : list){
		Listitem item = new Listitem(camelcaseToUiString(getFieldValue(o, displayfield).toString()));
		item.setParent(listbox);
		item.setAttribute("element", o);
	}
	}

	public void setSource(List source) {
	reinitialize();
	this.source = new LinkedList(source);
	sourceFed = true;
	sanitizeAndCreateListItems();
	}

	public void setTarget(List target) {
	reinitialize();
	this.target = target;
	targetFed = true;
	sanitizeAndCreateListItems();
	}
	
	private void reinitialize(){
	if(sourceFed && targetFed && this.source != null && this.target != null){
		sourceFed = false;
		targetFed = false;
		this.source = null;
		this.target = null;
	}
	}
	
	public void setBoxwidth(String boxwidth) {
	this.boxwidth = boxwidth;
	}

	public void setButtonwidth(String buttonwidth) {
	this.buttonwidth = buttonwidth;
	}

	public void setDisplayfield(String displayfield) {
	this.displayfield = displayfield;
	}

	private static final long serialVersionUID = 1L;
}