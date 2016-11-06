package com.fayaz.recmain.recommender.hibernate.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.fayaz.recmain.recommender.hibernate.HibernateUtil;
import com.fayaz.recmain.recommender.hibernate.pojo.Rating;
import com.fayaz.recmain.recommender.rest.pojo.RecommendationItem;

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
	
	public List<RecommendationItem> getTopRatedProducts(long customerId,int minRatingsForProduct,int count){
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<RecommendationItem> returnList = new LinkedList<RecommendationItem>();
		try {
			trns = session.beginTransaction();
			Query query = session.getNamedQuery("@SQL_GET_AVG_RATINGS_SORT_DESC");
			query.setLong("customerId", customerId);
			query.setInteger("minRatingsForProduct", minRatingsForProduct);
			List<Object[]> results = query.list();
			int i=1;
			for(Object[] row :results){
				RecommendationItem item = new RecommendationItem(Long.parseLong(row[0].toString()),Double.parseDouble(row[1].toString()));
				returnList.add(item);
				System.out.println(item.toString());
				if(++i == count)
					break;
			}
			
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		}finally {
			session.flush();
			session.close();
		}
		return returnList;
	}
}
