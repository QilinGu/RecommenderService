package com.fayaz.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.fayaz.hibernate.HibernateUtil;
import com.fayaz.hibernate.pojo.Algorithm;

public class AlgorithmDAO {
	
	private static final String INTERNAL_ERROR = "Internal Error";

	public void addAlgorithm(Algorithm algo) {
		boolean error = false;

		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			session.save(algo);
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

	
	public List<Algorithm> getAllAlgorithms() {

		boolean error = false;
		
		List<Algorithm> algos = new ArrayList<Algorithm>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {			
			algos = session.createQuery("from Algorithm").list();
		} catch (RuntimeException e) {
			e.printStackTrace();
			error = true;
		} finally {

			session.flush();
			session.close();
			if (error)
				throw new RuntimeException(INTERNAL_ERROR);
		}
		return algos;
	}

	public int deleteAlgorithm(String algoName) {
		boolean error = false;
		int rowCount = -1;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Transaction tx = session.beginTransaction();
			String hql = "delete from Algorithm where algoName = :algoname";
			Query query = session.createQuery(hql);
			query.setString("algoname", algoName);
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

	public void updateAlgorithm(Algorithm algo) {
		boolean error = false;

		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			session.update(algo);
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

	public Algorithm getAlgorithmByName(String algoName) {
		boolean error = false;
		String errMessage = "";

		Algorithm algo = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String queryString = "from Algorithm where algoName = :algoname";
			Query query = session.createQuery(queryString);
			query.setString("algoname", algoName);
			algo = (Algorithm) query.uniqueResult();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			if (error)
				throw new RuntimeException(errMessage);
		}
		return algo;
	}
	
	public Algorithm getAlgorithmById(long algoId) {
		boolean error = false;
		String errMessage = "";

		Algorithm algo = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String queryString = "from Algorithm where algoId = :algoid";
			Query query = session.createQuery(queryString);
			query.setLong("algoid", algoId);
			algo = (Algorithm) query.uniqueResult();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			if (error)
				throw new RuntimeException(errMessage);
		}
		return algo;
	}

}
