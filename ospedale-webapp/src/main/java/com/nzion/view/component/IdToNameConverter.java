package com.nzion.view.component;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listcell;

import com.nzion.domain.Party.PartyType;
import com.nzion.domain.Person;
import com.nzion.repository.common.CommonCrudRepository;

import java.io.Serializable;

public class IdToNameConverter implements TypeConverter {

    CommonCrudRepository repository = null;

    public IdToNameConverter() {
        repository = (CommonCrudRepository) SpringUtil.getBean("commonCrudRepository");
    }

    @Override
    public Object coerceToBean(Object value, Component component) {
        return null;
    }

    @Override
    public Object coerceToUi(Object value, Component component) {
        Listcell listcell = (Listcell) component;
        if(value!=null && component.getAttribute("partyType")!=null){
            Person person = repository.findUniqueByEquality(Person.class, new String[]{"id", "partyType"}, new Object[]{value, PartyType.valueOf((String) component.getAttribute("partyType"))});
            if(person!=null)
            listcell.setLabel(person.getFirstName() + " " + person.getLastName());
        }
        String className=(String)component.getAttribute("className");
        String property=(String)component.getAttribute("property");
        if(value!=null && className!=null){
            try {
                Object object = repository.findByPrimaryKey(this.getClass().getClassLoader().loadClass(className),(Serializable)value);
                String display=BeanUtils.getProperty(object,property);
                listcell.setLabel(display);
            } catch (Throwable e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return listcell;
    }

}
