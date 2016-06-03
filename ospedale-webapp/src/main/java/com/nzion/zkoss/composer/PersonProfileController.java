package com.nzion.zkoss.composer;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.nzion.domain.Person;
import com.nzion.domain.base.BaseEntity;
import com.nzion.service.PersonService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;
import com.nzion.util.ViewUtil;

public class PersonProfileController extends OspedaleAutowirableComposer {

	private static final long serialVersionUID = 1L;

	private PersonService personService;
	
	private final Person person = Infrastructure.getUserLogin().getPerson();
	
	private CommonCrudService commonCrudService;
	
	public void setCommonCrudService(CommonCrudService commonCrudService) {
	this.commonCrudService = commonCrudService;
	}

	public Person getPerson() {
	return person;
	}

	public void setPersonService(PersonService personService) {
	this.personService = personService;
	}
	
	public  <T> List<T> getPersonFavourites(Class<?> klass){
	List<T> personFavourites=new ArrayList<T>();
	personFavourites.addAll((Collection<T>) personService.getPersonFavourites(person, klass));
	return personFavourites;
	}
	
	public void saveFavourites(List<? extends BaseEntity> favouriteEntities){
	commonCrudService.save(favouriteEntities);
	com.nzion.util.UtilMessagesAndPopups.showSuccess();
	}
	
	public void deleteFavourites(final Listbox listbox, final List<? extends BaseEntity> list) throws InterruptedException{
	final List<?> toBeDeleted=com.nzion.util.ViewUtil.getSelectedItems(listbox);
	if(UtilValidator.isEmpty(toBeDeleted)){
		UtilMessagesAndPopups.showError("Please select items to delete");
		return;
	}
	Messagebox.show("Are You Sure ?", "Delete Confirm?",Messagebox.YES|Messagebox.NO,Messagebox.QUESTION, new EventListener() {
		
		@Override
		public void onEvent(Event evt) throws Exception {
			if("onYes".equalsIgnoreCase(evt.getName())){
				commonCrudService.delete(toBeDeleted);
				list.removeAll(toBeDeleted);
				Events.postEvent("onReloadRequest", listbox, null);
			}
			if("onNo".equalsIgnoreCase(evt.getName()))
				return;
		}
	});
	}
	
	public void favouriteAddClicked(Component parent, Listbox listboxToBeRefreshed, List<?> list,String uri){
	Window w = (Window) Executions.createComponents(uri,parent,com.nzion.util.UtilMisc.toMap("list", list,"person", person));
	w.addForward("onDetach", listboxToBeRefreshed, "onReloadRequest");
	}
	
	public void addPersonFavourites(Listbox listbox,Window window,List<BaseEntity> list, Class<? extends BaseEntity> klass, Class<? extends BaseEntity> entityType) throws Exception{
	List<? extends BaseEntity> favouritables = ViewUtil.getSelectedItems(listbox);
	if (favouritables.size() < 1) {
		com.nzion.util.UtilMessagesAndPopups.showError("Please select items to add");
		return;
	}
	for(BaseEntity favourite : favouritables){
		Constructor<? extends BaseEntity> c = klass.getConstructor(new Class[]{Person.class, entityType});
		BaseEntity o = c.newInstance(person, favourite);
		list.add(o);
	}
	window.detach();
	}

	
	
}