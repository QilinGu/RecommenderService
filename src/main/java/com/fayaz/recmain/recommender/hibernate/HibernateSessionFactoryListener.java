package com.fayaz.recmain.recommender.hibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.fayaz.recmain.recommender.index.DescriptionIndexer;
public class HibernateSessionFactoryListener implements ServletContextListener {
	

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		HibernateUtil.destroy();
		DescriptionIndexer.closeWriter();
		
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {		
		HibernateUtil.init();
		DescriptionIndexer.prepareReaderWriterAndSearcher();
	}
}
