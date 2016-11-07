package com.fayaz.recmain.recommender.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.fayaz.recmain.recommender.hibernate.HibernateUtil;
import com.fayaz.recmain.recommender.hibernate.pojo.AdminUser;

public class AdminUserDAO {
	
	private static final String INTERNAL_ERROR = "Internal Error";

	public void addAdminUser(AdminUser user) {
		boolean error = false;

		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			session.save(user);
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

	
	@SuppressWarnings("unchecked")
	public List<AdminUser> getAllAdminUsers() {

		boolean error = false;
		List<AdminUser> users = new ArrayList<AdminUser>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {			
			users = session.createQuery("from AdminUser").list();
		} catch (RuntimeException e) {
			e.printStackTrace();
			error = true;
		} finally {

			session.flush();
			session.close();
			if (error)
				throw new RuntimeException(INTERNAL_ERROR);
		}
		return users;
	}

	public int deleteAdminUser(String username) {
		boolean error = false;

		int rowCount = -1;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Transaction tx = session.beginTransaction();
			String hql = "delete from AdminUser where username = :username";
			Query query = session.createQuery(hql);
			query.setString("username", username);
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

	public void updateAdminUser(AdminUser user) {
		boolean error = false;

		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			session.update(user);
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
	
	public AdminUser getAdminUserByEmail(String email) {
		boolean error = false;
		AdminUser user = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String queryString = "from AdminUser where email = :email";
			Query query = session.createQuery(queryString);
			query.setString("email", email);
			user = (AdminUser) query.uniqueResult();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			if (error)
				throw new RuntimeException(INTERNAL_ERROR);
		}
		return user;
	}
	
	

	public AdminUser getAdminUserByUsername(String username) {
		boolean error = false;
		AdminUser user = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String queryString = "from AdminUser where username = :username";
			Query query = session.createQuery(queryString);
			query.setString("username", username);
			user = (AdminUser) query.uniqueResult();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			if (error)
				throw new RuntimeException(INTERNAL_ERROR);
		}
		return user;
	}

}
