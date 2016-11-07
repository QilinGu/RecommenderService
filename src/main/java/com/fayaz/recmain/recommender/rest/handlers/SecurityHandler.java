package com.fayaz.recmain.recommender.rest.handlers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import com.fayaz.recmain.recommender.deepconfig.TokenCache;
import com.fayaz.recmain.recommender.hibernate.dao.AdminUserDAO;
import com.fayaz.recmain.recommender.hibernate.dao.CustomerDAO;
import com.fayaz.recmain.recommender.hibernate.pojo.AdminUser;
import com.fayaz.recmain.recommender.hibernate.pojo.Customer;
import com.fayaz.recmain.recommender.rest.pojo.Login;
import com.fayaz.recmain.recommender.rest.pojo.Signup;

public class SecurityHandler {

	private static final SecureRandom RANDOM = new SecureRandom();

	public void doSignup(Signup signup) throws RuntimeException {

		CustomerDAO customerDAO = new CustomerDAO();
		Customer customer = customerDAO.getCustomerByName(signup
				.getCustomerName());
		if (customer == null)
			throw new RuntimeException("Customer does not exist!!!");
		String customerSalt = customer.getSalt();
		String dbHash = customer.getHash();
		String hash = getHashFromPassword(signup.getCustomerSecret()
				+ customerSalt);
		if (!hash.equals(dbHash))
			throw new RuntimeException("Customer Validation Failed!!!");

		AdminUserDAO adminUserDAO = new AdminUserDAO();
		if (!validateUserNameNotExists(signup.getUsername(), adminUserDAO))
			throw new RuntimeException("Username Already Exists");
		else if (!validateEmailNotExists(signup.getEmail(), adminUserDAO))
			throw new RuntimeException("Email Already Exists");
		else {
			AdminUser user = new AdminUser();
			user.setUserName(signup.getUsername());
			user.setEmail(signup.getEmail());
			String salt = getNextSalt();
			user.setPasswordHash(getHashFromPassword(signup.getPassword()
					+ salt));
			user.setSalt(salt);
			user.setCustomerId(1L);
			adminUserDAO.addAdminUser(user);
		}

	}

	public String doLogin(Login login) throws RuntimeException {
		AdminUserDAO adminUserDAO = new AdminUserDAO();
		AdminUser user = adminUserDAO.getAdminUserByUsername(login
				.getUsername());
		if (user == null)
			throw new RuntimeException("Username does not exist!!!");
		String salt = user.getSalt();
		String hash = getHashFromPassword(login.getPassword() + salt);
		String dbHash = user.getPasswordHash();
		if (hash.equals(dbHash)) {
			String clientToken = getNextSalt();
			TokenCache.putToCache(clientToken, user.getCustomerId());
			//MapDBRestClient.putToTokenMap(clientToken, ((Long)user.getCustomerId()).toString());
			return clientToken;
		} else {
			throw new RuntimeException("Incorrect password!!!");
		}

	}

	public boolean validateEmailNotExists(String email,
			AdminUserDAO adminUserDAO) {
		AdminUser user = adminUserDAO.getAdminUserByEmail(email);
		if (user == null)
			return true;
		return false;
	}

	public boolean validateUserNameNotExists(String username,
			AdminUserDAO adminUserDAO) {
		AdminUser user = adminUserDAO.getAdminUserByUsername(username);
		if (user == null)
			return true;
		return false;
	}

	public boolean validateUserNameNotExists(String username) {
		return validateUserNameNotExists(username, new AdminUserDAO());
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

	public String getNextSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < salt.length; i++) {
			sb.append(Integer.toString((salt[i] & 0xff) + 0x100, 16).substring(
					1));
		}
		return sb.toString();
	}

}
