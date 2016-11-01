package com.fayaz.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.fayaz.hibernate.HibernateUtil;
import com.fayaz.hibernate.pojo.Customer;

public class CustomerDAO {
	
	public void addCustomer(Customer customer) {
		boolean error = false;
		String errMessage = "";

		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			session.save(customer);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();

			error = true;
			errMessage = e.getMessage();
		} finally {
			session.flush();
			session.close();
			if (error)
				throw new RuntimeException(errMessage);
		}
	}

	
	public List<Customer> getAllCustomers() {

		boolean error = false;
		String errMessage = "";
		
		List<Customer> customers = new ArrayList<Customer>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {			
			customers = session.createQuery("from Customer").list();
		} catch (RuntimeException e) {
			e.printStackTrace();
			error = true;
			errMessage = e.getMessage();
		} finally {

			session.flush();
			session.close();
			if (error)
				throw new RuntimeException(errMessage);
		}
		return customers;
	}

	public int deleteCustomer(long id) {
		boolean error = false;
		String errMessage = "";

		int rowCount = -1;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Transaction tx = session.beginTransaction();
			String hql = "delete from Customer where customerId = :id";
			Query query = session.createQuery(hql);
			query.setLong("id", id);
			rowCount = query.executeUpdate();
			tx.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			error = true;
			errMessage = e.getMessage();
		} finally {

			session.flush();
			session.close();
			if (error)
				throw new RuntimeException(errMessage);
		}
		return rowCount;
	}

	public void updateCustomer(Customer customer) {
		boolean error = false;
		String errMessage = "";

		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			session.update(customer);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
			if (error)
				throw new RuntimeException(errMessage);
		} finally {
			session.flush();
			session.close();
		}
	}
	
	public Customer getCustomerById(long id) {
		boolean error = false;
		String errMessage = "";

		Customer customer = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String queryString = "from Customer where customerId = :id";
			Query query = session.createQuery(queryString);
			query.setLong("id", id);
			customer = (Customer) query.uniqueResult();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			if (error)
				throw new RuntimeException(errMessage);
		}
		return customer;
	}
	
	public Customer getCustomerByName(String name) {
		boolean error = false;
		String errMessage = "";

		Customer customer = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String queryString = "from Customer where customerName = :name";
			Query query = session.createQuery(queryString);
			query.setString("name", name);
			customer = (Customer) query.uniqueResult();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			if (error)
				throw new RuntimeException(errMessage);
		}
		return customer;
	}
}
