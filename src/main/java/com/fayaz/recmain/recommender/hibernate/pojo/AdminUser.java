package com.fayaz.recmain.recommender.hibernate.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="ADMIN_USERS", 
	   uniqueConstraints={@UniqueConstraint(columnNames={"admin_user_id"})})
public class AdminUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "admin_user_seq")
	@SequenceGenerator(name = "admin_user_seq", sequenceName = "rec_admin_sequence")
	@Column(name="admin_user_id", nullable=false, unique=true, length=18)
	private long adminUserId;
	
	@Column(name="username", length=360, nullable=false,unique=true)
	private String userName;
	
	@Column(name="email", length=360, nullable=false,unique=true)
	private String email;
	
	@Column(name="password_hash", length=360, nullable=false,unique=true)
	private String passwordHash;
	
	@Column(name="salt", length=120, nullable=false,unique=true)
	private String salt;
	
	@Column(name="customer_id", length=18, nullable=false)
	private long customerId;
	
	
	public long getAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(long adminUserId) {
		this.adminUserId = adminUserId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	} 

}
