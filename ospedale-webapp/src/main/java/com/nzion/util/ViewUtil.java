package com.nzion.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.zhtml.Li;
import org.zkoss.zhtml.Ul;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.SimpleConstraint;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.impl.InputElement;

import com.nzion.domain.Person;
import com.nzion.domain.Referral;
import com.nzion.domain.Salutable;
import com.nzion.domain.UserLogin;
import com.nzion.domain.annot.DisplayNameField;
import com.nzion.enums.FieldType;
import com.nzion.service.common.CommonCrudService;
import com.nzion.view.component.EnumerationDropdown;
import com.nzion.view.component.IdSequenceHBox;
import com.nzion.view.component.LookupBox;

public class ViewUtil {
	private static Map<FieldType, Class<? extends BindComponent>> componentBindingMap = new HashMap<FieldType, Class<? extends BindComponent>>();
	static {
		componentBindingMap.put(FieldType.TEXTBOX, TextBoxBinder.class);
		componentBindingMap.put(FieldType.CHECKBOX, CheckBoxBinder.class);
		componentBindingMap.put(FieldType.ENUM, EnumBinder.class);
		componentBindingMap.put(FieldType.ENTITY, LookupBinder.class);
		componentBindingMap.put(FieldType.DATEBOX, DateBoxBinder.class);
		componentBindingMap.put(FieldType.INTBOX, IntBoxBinder.class);
	}

	public static Component getComponent(Map<String, Object> viewMap, DataBinder binder) throws InstantiationException,
			IllegalAccessException {
	FieldType fieldtype = (FieldType) viewMap.get(Constants.FIELD_TYPE);
	Long seqNum = (Long) viewMap.get(Constants.SEQNUM);
	Component component = null;
	String Id = viewMap.get(Constants.ID).toString();
	String compId = null;

	if (componentBindingMap.get(fieldtype) != null) {
		component = componentBindingMap.get(fieldtype).newInstance().addBinding(viewMap, binder);
	}

	compId = Constants.PREFERENCE_COMPONENT_PREFIX + Id;

	if (seqNum != null) {
		component.setAttribute("seq", seqNum.toString());
	}
	component.setId(compId);
	Boolean isMandatory = (Boolean) viewMap.get(Constants.ISMANDATORY);
	if (isMandatory != null && isMandatory) component = setMandatoryConstraint(component);
	return component;
	}

	private static Component setMandatoryConstraint(Component component) {
	if (component instanceof InputElement) {
		((InputElement) component).setConstraint(new SimpleConstraint(SimpleConstraint.NO_EMPTY));
	}
	return component;
	}

	@SuppressWarnings("unchecked")
	public static void clearComponents(Component parent) {
	if (parent == null) return;
	List<? extends Component> children = parent.getChildren();
	if (UtilValidator.isNotEmpty(children)) {
		children.clear();
	}
	}

	public static <T> void createBullets(Component parent, Collection<T> data, String[] dataProperties,
			String labelsSeparator) {
	if (UtilValidator.isEmpty(labelsSeparator)) {
		labelsSeparator = "\t";
	}
	for (T t : data) {
		Ul ul = new Ul();
		ul.setParent(parent);
		Li li = new Li();
		li.setParent(ul);
		int counter = 1;
		for (String dataProperty : dataProperties) {
			Object o = t;
			for (String fieldName : dataProperty.split("\\.")) {
				o = UtilReflection.getFieldValue(o, fieldName);
				if (o == null) {
					break;
				}

			}
			String labelValue = o == null ? "" : o.toString();
			Label label = null;
			if (counter < dataProperties.length) {
				label = new Label(labelValue + labelsSeparator);
			} else {
				label = new Label(labelValue);
			}
			label.setStyle("font-weight:normal");
			label.setParent(li);
			counter++;
		}
	}
	}

	public static <T> void createBullets(Component parent, Collection<T> data, String[] dataProperties) {
	String defaultSeparator = "\t";
	createBullets(parent, data, dataProperties, defaultSeparator);
	}

	public static Label getLabel(Map<String, Object> viewMap) {
	return getLabel(viewMap, null);
	}

	public static Label getLabel(Map<String, Object> viewMap, Map<String, Object> data) {
	Label lbl = new Label();
	String labelId = null;
	labelId = Constants.LABEL_PREFIX + viewMap.get(Constants.ID).toString();
	lbl.setId(labelId);
	String newLabel = (String) viewMap.get(Constants.NEW_LABEL);
	if (viewMap.get("listenerevent") != null && data != null) {
		data.put(labelId, lbl);
		lbl.setValue(newLabel);
	}
	if (UtilValidator.isNotEmpty(newLabel)) {
		lbl.setValue(newLabel);
	} else {
		lbl.setValue((String) viewMap.get(Constants.LABEL));
	}
	return lbl;
	}

	public static Component createSequenceComponent(Map<String, Object> preferenceMap, String a_prefix,
			Long a_startIndex, String a_suffix, Long a_incrementBy, String a_entityName) {
	String id = preferenceMap.get(Constants.ID).toString();
	String caption = preferenceMap.get(Constants.NEW_LABEL).toString();
	String prefix = a_prefix;
	String startIndex = a_startIndex == null ? null : a_startIndex.toString();
	String suffix = a_suffix;
	String incrementBy = a_incrementBy == null ? null : a_incrementBy.toString();
	String entityName = a_entityName;
	IdSequenceHBox idSequenceGroupBox = new IdSequenceHBox.Builder(id, prefix, startIndex, suffix, incrementBy)
			.setCaption(caption).setEntityName(entityName).build();
	idSequenceGroupBox.setId(Constants.PREFERENCE_COMPONENT_PREFIX + id);
	return idSequenceGroupBox;
	}

	public static String getFormattedName(Object entity) {
	CommonCrudService service = Infrastructure.getSpringBean("commonCrudService");
	return service.getFormattedName((Salutable)entity);
	}

	public static String getEntityFormattedName(Object otherPatientEntity, String updateLabelToValuePropertyName) {
	if (otherPatientEntity instanceof Person) {
		return ViewUtil.getFormattedName(otherPatientEntity);
	} else
		if (UtilValidator.isNotEmpty(updateLabelToValuePropertyName)) {
			return UtilReflection.getSimpleProperty(otherPatientEntity, updateLabelToValuePropertyName);
		} else {
			String nameField = otherPatientEntity.getClass().getAnnotation(DisplayNameField.class).value();
			return UtilReflection.getSimpleProperty(otherPatientEntity, nameField);
		}
	}

	public static Timebox getReadonlyTimebox(Date time) {
	Timebox timebox = new Timebox(time);
	timebox.setReadonly(true);
	timebox.setButtonVisible(false);
	timebox.setFormat("KK:mm a");
	timebox.setWidth("100px");
	timebox.setStyle("background:transparent; border: none;");
	return timebox;
	}

	public static <T> T getLookUpBoxValueByAccountNumber(LookupBox lookupBox, Class<T> klass) {
	CommonCrudService commonCrudService = Infrastructure.getSpringBean("commonCrudService");
	if (klass == null || lookupBox.getValue() == null) return null;
	return commonCrudService.getByAccountNumber(klass, lookupBox.getValue());
	}

	public static <T> T getLookUpBoxValueForUniqueValue(LookupBox lookupBox, Class<T> klass) {
	CommonCrudService commonCrudService = Infrastructure.getSpringBean("commonCrudService");
	String requiredcolumn = lookupBox.getRequiredcolumn();
	if (klass == null || requiredcolumn == null || lookupBox.getValue() == null) return null;
	return commonCrudService.getByUniqueValue(klass, requiredcolumn, lookupBox.getValue());
	}

	private final static String BINDING_EXPR = "data.";

	private interface BindComponent {
		Component addBinding(Map<String, Object> viewMap, DataBinder binder);
	}

	private static class IntBoxBinder implements BindComponent {

		IntBoxBinder() {
		};

		public Component addBinding(Map<String, Object> viewMap, DataBinder binder) {
		String Id = viewMap.get(Constants.ID).toString();
		Intbox intbox = new Intbox();
		binder.addBinding(intbox, "value", BINDING_EXPR + Id);
		return intbox;
		}
	}

	private static class TextBoxBinder implements BindComponent {
		TextBoxBinder() {
		};

		public Component addBinding(Map<String, Object> viewMap, DataBinder binder) {
		String Id = viewMap.get(Constants.ID).toString();
		Textbox textbox = new Textbox();
		binder.addBinding(textbox, "value", BINDING_EXPR + Id);
		return textbox;
		}
	}

	private static class EnumBinder implements BindComponent {
		EnumBinder() {
		};

		public Component addBinding(Map<String, Object> viewMap, DataBinder binder) {
		String Id = viewMap.get(Constants.ID).toString();
		String defaultValue = (String) viewMap.get(Constants.DEFAULT_FIELD_VALUE);
		String requiredColumn = (String) viewMap.get(Constants.REQUIRED_COLUMN);
		EnumerationDropdown enumerationDropdown = new EnumerationDropdown();
		enumerationDropdown.setEnumType(requiredColumn);
		enumerationDropdown.setValue(defaultValue);
		Events.postEvent("onCreate", enumerationDropdown, null);
		binder.addBinding(enumerationDropdown, "selectedItem", BINDING_EXPR + Id);
		return enumerationDropdown;
		}
	}

	private static class LookupBinder implements BindComponent {
		LookupBinder() {
		};

		public Component addBinding(Map<String, Object> viewMap, DataBinder binder) {
		String requiredColumn = (String) viewMap.get(Constants.REQUIRED_COLUMN);
		String searchColumns = (String) viewMap.get(Constants.SEARCH_COLUMN);
		String displayColumns = (String) viewMap.get(Constants.DISPLAY_COLUMN);
		String Id = viewMap.get(Constants.ID).toString();
		String entityClassName = (String) viewMap.get(Constants.DEFAULT_FIELD_VALUE);
		if (UtilValidator.isEmpty(requiredColumn) || UtilValidator.isEmpty(searchColumns)
				|| UtilValidator.isEmpty(displayColumns)) {
			throw new IllegalArgumentException("LookupBox cannot be constructed.Please contact administrator");
		}

		LookupBox lookupBox = new LookupBox(requiredColumn, entityClassName, searchColumns, displayColumns);
		binder.addBinding(lookupBox, "value", BINDING_EXPR + Id);
		return lookupBox;
		}

	}

	private static class CheckBoxBinder implements BindComponent {

		CheckBoxBinder() {
		};

		public Component addBinding(Map<String, Object> viewMap, DataBinder binder) {
		Checkbox checkbox = new Checkbox();
		String Id = viewMap.get(Constants.ID).toString();
		binder.addBinding(checkbox, "checked", BINDING_EXPR + Id);
		return checkbox;
		}
	}

	private static class DateBoxBinder implements BindComponent {

		DateBoxBinder() {
		};

		public Component addBinding(Map<String, Object> viewMap, DataBinder binder) {
		Datebox datebox = new Datebox();
		String Id = viewMap.get(Constants.ID).toString();
		binder.addBinding(datebox, "value", BINDING_EXPR + Id);
		return datebox;
		}
	}

	public static <T> Set<T> getSelectedItemsFromListBox(Set<Listitem> selectedItems) {
	return getSelectedItemsFromListBox(selectedItems, new HashSet<T>());
	}

	@SuppressWarnings("unchecked")
	public static <T> Set<T> getSelectedItemsFromListBox(Set<Listitem> selectedItems, Set<T> resultSet) {
	for (Listitem selectedItem : selectedItems) {
		resultSet.add((T) selectedItem.getValue());
	}
	return resultSet;
	}
	
	public static void swapChildComponents(Component child1, Component child2, Component parent){
	int index1 = parent.getChildren().indexOf(child1);
	int index2 = parent.getChildren().indexOf(child2);
	parent.getChildren().remove(child1);
	child1.setParent(null);
	parent.getChildren().add(index2, child1);					
	parent.getChildren().remove(child2);
	child2.setParent(null);
	parent.getChildren().add(index1, child2);					
	}

	@SuppressWarnings("unchecked")
	public static <T> T getSelectedItem(Listbox listbox) {
	Listitem selectedItem = listbox.getSelectedItem();
	if (selectedItem != null) return (T) selectedItem.getValue();
	return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getSelectedItems(Listbox listbox) {
	Set<Listitem> selectedItems = listbox.getSelectedItems();
	List<Object> selectedObjects = new ArrayList<Object>();
	for (Listitem listitem : selectedItems)
		selectedObjects.add(listitem.getValue());
	return (List<T>) selectedObjects;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getItems(Listbox listbox) {
	List<Listitem> selectedItems = listbox.getItems();
	List<Object> selectedObjects = new ArrayList<Object>();
	for (Listitem listitem : selectedItems)
		selectedObjects.add(listitem.getValue());
	return (List<T>) selectedObjects;
	}

	public static void validateEmail(Textbox textbox) {
	String email = textbox.getValue();
	if (UtilValidator.isNotEmpty(email) && !UtilValidator.validateEmail(email)) {
		throw new WrongValueException(textbox, "Invalid Email format");
	}
	}

	public static void setDisabled(boolean disabled, InputElement... elements) {
	for (InputElement element : elements) {
		element.setDisabled(disabled);
	}
	}

	public static void resetImpersonateSetting() {
	UserLogin userLogin = Infrastructure.getUserLogin();
	if (userLogin.isImpersonated()) {
		userLogin.setImpersonated(false);
		userLogin.setPerson(userLogin.getOriginalPerson());
		userLogin.setOriginalPerson(null);
		Executions.getCurrent().sendRedirect("/home.zul", "_home");
	}
	}

	public static void enableImpersonateSetting(Person person) {
	if (person == null) return;
	UserLogin userLogin = Infrastructure.getUserLogin();
	if (!userLogin.isImpersonated()) {
		userLogin.setImpersonated(true);
		userLogin.setOriginalPerson(userLogin.getPerson());
	}
	userLogin.setPerson(person);
	Executions.getCurrent().sendRedirect("/home.zul", "_home");
	}

	public static void disPlayUniqueErrorMessage(Exception exception, String message) {
	if (exception instanceof DataIntegrityViolationException) {
		DataIntegrityViolationException dataException = (DataIntegrityViolationException) exception;
		if (dataException.getCause() != null && dataException.getCause() instanceof ConstraintViolationException) {
			int code = JDBCExceptionHelper.extractErrorCode(((ConstraintViolationException) (dataException.getCause()))
					.getSQLException());
			if (1062 == code) {
				UtilMessagesAndPopups.showError(message);
			}
		}
	}
	}
	
	public static String getReferralName(Referral referral) {
	if (referral == null) return null;
	StringBuilder name = new StringBuilder();
	name.append(referral.getFirstName() == null ? "" : referral.getFirstName());
	name.append(Constants.BLANK);
	name.append(referral.getLastName() == null ? "" : referral.getLastName());
	return name.toString();
	}
	
}
