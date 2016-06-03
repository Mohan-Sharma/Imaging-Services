package com.nzion.hibernate.ext;

import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;

public class InfrastructureSessionFactoryObserver implements SessionFactoryObserver {

	private SessionFactoryObserver observer;
	private static final long serialVersionUID = -5173918323644023024L;
	Boolean permissionSeeded;

	public InfrastructureSessionFactoryObserver() {
	}

	public InfrastructureSessionFactoryObserver(SessionFactoryObserver observer, Boolean permissionSeeded) {
	this.observer = observer;
	this.permissionSeeded = permissionSeeded;
	}

	public void sessionFactoryClosed(SessionFactory sessionFactory) {
	if (observer != null) observer.sessionFactoryClosed(sessionFactory);
	}

	public void sessionFactoryCreated(SessionFactory sessionFactory) {
	if (observer != null) observer.sessionFactoryCreated(sessionFactory);
	}

	public void setObserver(SessionFactoryObserver observer) {
	this.observer = observer;
	}

}
