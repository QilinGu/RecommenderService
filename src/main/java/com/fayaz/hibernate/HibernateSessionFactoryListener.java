package com.fayaz.hibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
public class HibernateSessionFactoryListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
		HibernateUtil.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		HibernateUtil.init();
	}
}
