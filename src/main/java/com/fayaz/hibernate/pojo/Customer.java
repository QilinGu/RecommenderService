package com.fayaz.hibernate.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="CUSTOMERS", 
	   uniqueConstraints={@UniqueConstraint(columnNames={"customer_id"})})
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "customer_seq")
	@SequenceGenerator(name = "customer_seq", sequenceName = "rec_admin_sequence")
	@Column(name="customer_id", nullable=false, unique=true, length=18)
	private long customerId;
	
	@Column(name="customer_name", length=360, nullable=false,unique=true)
	private String customerName;
	
	@Column(name="hash", length=360, nullable=false,unique=true)
	private String hash;
	
	@Column(name="salt", length=120, nullable=false,unique=true)
	private String salt;
	
	@Column(name="algo_id", length=18, nullable=false)
	private long algoId;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public long getAlgoId() {
		return algoId;
	}

	public void setAlgoId(long algoId) {
		this.algoId = algoId;
	}

}
