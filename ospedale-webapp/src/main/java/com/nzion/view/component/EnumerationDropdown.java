package com.nzion.view.component;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import com.nzion.domain.Enumeration;
import com.nzion.service.common.EnumerationService;
import com.nzion.util.Infrastructure;

/**
 * @author Sandeep Prusty
 *         Apr 19, 2010
 */
public class EnumerationDropdown extends Combobox {

    private String enumType;

    private String selectedValue;

    private String selectFirst = "true";

    public EnumerationDropdown() {
        super();
        super.setReadonly(true);
        setItemRenderer(new ComboitemRenderer() {
            public void render(Comboitem item, Object o, int index) throws Exception {
                if (o == null) return;
                Enumeration enumeration = (Enumeration) o;
                item.setLabel(enumeration.getDescription());
                item.setValue(enumeration);
            }
        });
        this.addEventListener(Events.ON_CREATE, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
//			setValue(selectedValue);
                if (enumType != null) {
                    EnumerationService service = Infrastructure.getSpringBean("enumerationService");
                    List<Enumeration> enumerations = service.getRelevantEnumerationsByType(enumType);
                    setModel(new BindingListModelList(enumerations, true));
                }
                if (!Boolean.valueOf(selectFirst))
                    return;
                if (getItems().size() > 0)
                    ((EnumerationDropdown) event.getTarget()).setSelectedItem((Comboitem) getItems().get(0));
            }
        });
    }

    public String getEnumType() {
        return enumType;
    }

    public void setEnumType(String enumType) {
        this.enumType = enumType;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(final String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public void setSelectFirst(String selectFirst) {
        this.selectFirst = selectFirst;
    }

    private static final long serialVersionUID = 1L;
}