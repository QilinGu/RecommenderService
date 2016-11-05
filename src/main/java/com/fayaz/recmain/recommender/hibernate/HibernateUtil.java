package com.fayaz.recmain.recommender.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.fayaz.recmain.recommender.util.StringRepo;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static void destroy() {
		if (sessionFactory != null && !sessionFactory.isClosed()) {
			sessionFactory.close();
		}
	}
	
	public static SessionFactory getSessionFactory() throws RuntimeException{
		if(sessionFactory==null)
			throw new RuntimeException("Hibernate Session Factory not yet initialized.");
		return sessionFactory;
	}

	public static void init() {
		if (sessionFactory == null) {
			Configuration configuration = new Configuration();
			configuration.configure(StringRepo.HIBERNATE_CONFIG_PATH);
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
	}

}
