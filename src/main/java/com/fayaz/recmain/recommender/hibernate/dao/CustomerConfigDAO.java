package com.fayaz.recmain.recommender.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.fayaz.recmain.recommender.hibernate.HibernateUtil;
import com.fayaz.recmain.recommender.hibernate.pojo.CustomerConfig;

public class CustomerConfigDAO {
	
	private static final String INTERNAL_ERROR = "Internal Error";
	
	public void addCustomerConfig(CustomerConfig config) {
		boolean error = false;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			session.save(config);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
			error = true;
		} finally {
			session.flush();
			session.close();
			if (error)
				throw new RuntimeException(INTERNAL_ERROR);
		}
	}
	
	public int deleteCustomerConfig(long custId) {
		boolean error = false;

		int rowCount = -1;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Transaction tx = session.beginTransaction();
			String hql = "delete from CustomerConfig where customerId = :custId";
			Query query = session.createQuery(hql);
			query.setLong("custId", custId);
			rowCount = query.executeUpdate();
			tx.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			error = true;
		} finally {

			session.flush();
			session.close();
			if (error)
				throw new RuntimeException(INTERNAL_ERROR);
		}
		return rowCount;
	}

	public void updateCustomerConfig(CustomerConfig config) {
		boolean error = false;

		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			session.update(config);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
			if (error)
				throw new RuntimeException(INTERNAL_ERROR);
		} finally {
			session.flush();
			session.close();
		}
	}
	
	public CustomerConfig getConfigByCustomerId(long custId) {
		boolean error = false;
		CustomerConfig config = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String queryString = "from CustomerConfig where customerId = :custId";
			Query query = session.createQuery(queryString);
			query.setLong("custId", custId);
			config = (CustomerConfig) query.uniqueResult();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			if (error)
				throw new RuntimeException(INTERNAL_ERROR);
		}
		return config;
	}
}
