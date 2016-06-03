package com.nzion.zkoss.composer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.nzion.domain.Person;
import com.nzion.domain.PersonDelegation;
import com.nzion.domain.Roles;
import com.nzion.service.PersonService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilMisc;
import com.nzion.util.UtilValidator;

public class PersonDelegationController extends OspedaleAutowirableComposer {
	
	private PersonService personService;
	
	private CommonCrudService commonCrudService;
	
	private Set<PersonDelegation> selectedPersonDelegations;
	
	private Set<PersonDelegation> personDelegations;
	
	private List<PersonDelegation> existingPersonDelegations = null;
	
	private PersonDelegation personDelegation;
	
	private Person person = Infrastructure.getLoggedInPerson();
	
	@Override
	public void doAfterCompose(Component component){
	if(Roles.hasRole(Roles.ADMIN)){
		personDelegations = new HashSet<PersonDelegation>(commonCrudService.getAll(PersonDelegation.class));
		return;
	}
	personDelegations = new HashSet<PersonDelegation>(personService.getPersonDelegationFor(person,null,null));
	}

	public void saveProviderDelegation(Component component){
	existingPersonDelegations = personService.getPersonDelegationFor(person, personDelegation.getFromDate(), personDelegation.getThruDate());
	if(personDelegation.getFromDate().compareTo(personDelegation.getThruDate()) == 1){
		UtilMessagesAndPopups.showError("Thru Date Must Be After From Date");
		return;
	}
		
	if(UtilValidator.isNotEmpty(existingPersonDelegations)){
		UtilMessagesAndPopups.showError("Cannot be delegate; Already delegated within this period");
		return;
	}
	personDelegation.setPerson(person);
	PersonDelegation savedPersonDelegation= commonCrudService.save(personDelegation);
	personDelegations.add(savedPersonDelegation);
	UtilMessagesAndPopups.showSuccess();
	component.detach();
	}
	
	public void openProviderDelegationWindow(Listbox listboxToBeRefreshed,PersonDelegation personDelegation){
	Window window =(Window) Executions.createComponents("/person/newEmergencyContact.zul", null, UtilMisc.toMap("entity", personDelegation,"personDelegationController",this,"personDelegations",personDelegations));
	window.addForward("onDetach", listboxToBeRefreshed, "onReloadRequest");
	}
	
	public void deleteProviderDelegations(Listbox listboxToBeRefreshed){
	personDelegations.removeAll(selectedPersonDelegations);
	commonCrudService.delete(selectedPersonDelegations);
	Events.postEvent("onReloadRequest", listboxToBeRefreshed, null);
	UtilMessagesAndPopups.showSuccess();
	}
	
	public void setPersonService(PersonService personService) {
	this.personService = personService;
	}

	public void setCommonCrudService(CommonCrudService commonCrudService) {
	this.commonCrudService = commonCrudService;
	}

	public Set<PersonDelegation> getSelectedPersonDelegations() {
	return selectedPersonDelegations;
	}

	public void setSelectedPersonDelegations(Set<PersonDelegation> selectedPersonDelegations) {
	this.selectedPersonDelegations = selectedPersonDelegations;
	}

	public Set<PersonDelegation> getPersonDelegations() {
	return personDelegations;
	}

	public PersonDelegation getPersonDelegation() {
	return personDelegation;
	}

	public void setPersonDelegation(PersonDelegation personDelegation) {
	this.personDelegation = personDelegation;
	}

	private static final long serialVersionUID = 1L;

}
