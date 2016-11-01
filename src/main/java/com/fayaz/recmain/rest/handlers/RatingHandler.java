package com.fayaz.recmain.rest.handlers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.fayaz.hibernate.dao.CustomerDAO;
import com.fayaz.hibernate.dao.RatingsDAO;
import com.fayaz.hibernate.pojo.Customer;
import com.fayaz.hibernate.pojo.Rating;
import com.fayaz.recmain.rest.pojo.RatingItem;

public class RatingHandler {

	public int addRatings(List<RatingItem> ratings,long customerId,String customerSecret) throws RuntimeException{
		CustomerDAO customerDao = new CustomerDAO();
		Customer customer = customerDao.getCustomerById(customerId);
		String salt = customer.getSalt();
		String hashGenerated = getHashFromPassword(customerSecret+salt);
		String hashInDb = customer.getHash();
		if(!hashGenerated.equals(hashInDb))
			throw new RuntimeException("Customer Id - Secret COnfiguration mismatch");		
		RatingsDAO dao = new RatingsDAO();
		int count = 0;
		for(RatingItem item:ratings){
			Rating newDbRating = new Rating(item.getUserId(),item.getProductId(),customerId,item.getRating());
			dao.addRating(newDbRating);
			count++;
		}
		return count;
	}
	
	
	public String getHashFromPassword(String saltPass) throws RuntimeException {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
		md.update(saltPass.getBytes());
		byte byteData[] = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}

}
