package com.nzion.zkoss.composer;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import com.nzion.repository.common.CommonCrudRepository;

public class GenericComposer extends OspedaleAutowirableComposer {

	private static final long serialVersionUID = 1L;
	private Class<?> entityClass;
	private String entityName;
	@Resource(name = "commonCrudRepository")
	private CommonCrudRepository repository;

	private Object selected;

	private Paging entityPaging;

	private Grid dataListViewGrid;

	private Boolean editMode = false;

	private int pageNumber = 0;

	private int pageSize = 10;

	private DataBinder binder;

	private String filterQuery;
	private int totalSize;

	public GenericComposer() {
	super();
	}

	/**
	 * This constructor is invoked from a Entity List
	 * page.
	 * 
	 * @param entityClassName
	 */
	public GenericComposer(String entityName) {
	super();
	this.entityName = entityName;
	try {
		entityClass = this.getClass().getClassLoader().loadClass(entityName);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	StringBuilder qBuilder = new StringBuilder("SELECT o FROM " + this.entityName + " o WHERE ");
	for (Method m : entityClass.getDeclaredMethods()) {
		if (m.getReturnType().equals(String.class)) {
			String fName = StringUtils.uncapitalize(m.getName().substring(3));
			qBuilder.append(fName + " like %:" + fName + "% ");
			qBuilder.append(" OR ");
		}
	}
	filterQuery = qBuilder.toString();
	filterQuery = filterQuery.substring(0, filterQuery.length() - 2);
	}

	public BindingListModel getAll() {
	String QUERY_ALL = " SELECT o FROM " + entityName + " o ";
	totalSize = repository.findCount(entityName).intValue();
	return new BindingListModelList(repository.findByQuery(QUERY_ALL, (pageNumber * pageSize), pageSize), false);
	}

	@Override
	public void doAfterCompose(Component component) throws Exception {
	super.doAfterCompose(component);
	selected = Executions.getCurrent().getArg().get("entity");
	if(selected==null && entityClass!=null)
		selected = entityClass.newInstance(); 
	// When navigating from List to Edit page, we pass the entity in the argument.
	setSelected(selected);
	if (entityPaging != null) pageSize = entityPaging.getPageSize();
	binder = new AnnotateDataBinder(component);
	binder.loadAll();
	}

	public void refreshModel() {
	binder.loadAttribute(dataListViewGrid, "model");
	}

	public void onPaging$entityPaging(ForwardEvent event) {
	PagingEvent pagingEvent = (PagingEvent) event.getOrigin();
	pageNumber = pagingEvent.getActivePage();
	refreshModel();
	}

	public void setEditMode(boolean b) {
	editMode = b;
	}

	public boolean isViewMode() {
	return !editMode;
	}

	public boolean isEditMode() {
	return editMode;
	}

	public boolean isNotSelected() {
	return this.selected == null;
	}

	public Object getSelected() {
	return selected;
	}

	public void setSelected(Object selected) {
	this.selected = selected;
	}

	public void onClick$save(Event event) {
	performSave();
	}

	private void performSave() {
	repository.save(selected);
	}

	public int getTotalSize() {
	return totalSize;
	}

	public void setTotalSize(int totalSize) {
	this.totalSize = totalSize;
	}

}