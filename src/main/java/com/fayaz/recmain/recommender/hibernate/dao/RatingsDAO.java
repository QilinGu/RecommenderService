package com.fayaz.recmain.recommender.hibernate.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.fayaz.recmain.recommender.hibernate.HibernateUtil;
import com.fayaz.recmain.recommender.hibernate.pojo.Rating;

public class RatingsDAO {
	
	private static final String INTERNAL_ERROR = "Internal Error";
	
	public void addRating(Rating user) throws RuntimeException{
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

	
	public List<Rating> getAllRatings() {

		boolean error = false;
		List<Rating> users = new ArrayList<Rating>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {			
			users = session.createQuery("from Rating").list();
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

	public int deleteRatingsByCustomer(long customerId) {
		boolean error = false;

		int rowCount = -1;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Transaction tx = session.beginTransaction();
			String hql = "delete from Rating where customerId = :customerId";
			Query query = session.createQuery(hql);
			query.setLong("customerId", customerId);
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
    
    public int deleteRatingsByUser(long userId) {
		boolean error = false;

		int rowCount = -1;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Transaction tx = session.beginTransaction();
			String hql = "delete from Rating where userId = :userId";
			Query query = session.createQuery(hql);
			query.setLong("userId", userId);
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

	public void updateRating(Rating rating) {
		boolean error = false;

		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			session.update(rating);
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
}
