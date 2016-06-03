package com.nzion.zkoss.composer;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;

import com.nzion.util.Infrastructure;

/**
 * @author Sandeep Prusty
 * Apr 23, 2010
 */
public class OspedaleAutowirableComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;

	protected Component root;

	public OspedaleAutowirableComposer() {
	super();
	ApplicationContext springContext = Infrastructure.getApplicationContext();
	AutowireCapableBeanFactory wirableFactory = springContext.getAutowireCapableBeanFactory();
	wirableFactory.autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
	}

	@Override
	public void doAfterCompose(Component component) throws Exception {
	component.setAttribute("controller", this);
	root = component;
	super.doAfterCompose(component);
	}

}