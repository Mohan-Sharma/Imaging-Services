package com.nzion.view.component;

import java.io.IOException;
import java.sql.SQLException;

import org.zkoss.bind.BindContext;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Image;

import com.nzion.domain.Person;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;

public class GenderImageConverter implements TypeConverter ,org.zkoss.bind.Converter{

	private static CommonCrudService commonCrudService = Infrastructure.getSpringBean("commonCrudService");

	public Object coerceToBean(java.lang.Object val, org.zkoss.zk.ui.Component comp) {
	return null;
	}

	public static void coerceToUiStatic(java.lang.Object val, org.zkoss.zk.ui.Component comp) {
	if (val == null) {
		((Image) comp).setSrc("/images/blank.gif");
		return;
	}
	Person person = commonCrudService.getById(Person.class, ((Person) val).getId());
	if (person.getProfilePicture() != null) {
		try {
			((Image) comp).setContent(new org.zkoss.image.AImage("Patient Image", person.getProfilePicture()
					.getResource().getBinaryStream()));
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} else
		if (person.getGender() != null) {
			if ("M".equals(person.getGender().getEnumCode()))
				((Image) comp).setSrc("/images/blank-male.gif");
			else
				if ("F".equals(person.getGender().getEnumCode())) ((Image) comp).setSrc("/images/blank-female.gif");
		} else {
			((Image) comp).setSrc("/images/blank.gif");
		}
	}

	public Object coerceToUi(java.lang.Object val, org.zkoss.zk.ui.Component comp) {
        coerceToUiStatic(val, comp);
	return null;
	}

    @Override
    public Object coerceToUi(Object val, Component component, BindContext bindContext) {
        coerceToUiStatic(val, component);
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object coerceToBean(Object o, Component component, BindContext bindContext) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
