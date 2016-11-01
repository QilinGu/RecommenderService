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
@Table(name="CUST_CONFIG", 
	   uniqueConstraints={@UniqueConstraint(columnNames={"CUST_CONFIG_ID"})})
public class CustomerConfig {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "config_seq")
	@SequenceGenerator(name = "config_seq", sequenceName = "rec_admin_sequence")
	@Column(name="CUST_CONFIG_ID", nullable=false, unique=true, length=18)
	private long custConfigId;
	
	@Column(name="CUSTOMER_ID", length=18, nullable=false)
	private long customerId;	

	@Column(name="ALGO_ID", length=18, nullable=false)
	private long algoId;
	
	@Column(name="HYBRID_USER_PERC", length=18)
	private float hybridUserPerc;
	
	@Column(name="HYBRID_ITEM_PERC", length=18)
	private float hybridItemPerc;
	
	@Column(name="HYBRID_CONTENT_PERC", length=18)
	private float hybridContentPerc;
	
	@Column(name="RETURN_COUNT", length=18, nullable=false)
	private long returnCount;
	
	@Column(name="NON_PERSONAL_THRESHOLD", length=18, nullable=false)
	private long nonPersonalThreshold;
	
	public long getCustConfigId() {
		return custConfigId;
	}

	public void setCustConfigId(long custConfigId) {
		this.custConfigId = custConfigId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getAlgoId() {
		return algoId;
	}

	public void setAlgoId(long algoId) {
		this.algoId = algoId;
	}

	public float getHybridUserPerc() {
		return hybridUserPerc;
	}

	public void setHybridUserPerc(float hybridUserPerc) {
		this.hybridUserPerc = hybridUserPerc;
	}

	public float getHybridItemPerc() {
		return hybridItemPerc;
	}

	public void setHybridItemPerc(float hybridItemPerc) {
		this.hybridItemPerc = hybridItemPerc;
	}

	public float getHybridContentPerc() {
		return hybridContentPerc;
	}

	public void setHybridContentPerc(float hybridContentPerc) {
		this.hybridContentPerc = hybridContentPerc;
	}

	public long getReturnCount() {
		return returnCount;
	}

	public void setReturnCount(long returnCount) {
		this.returnCount = returnCount;
	}

	public long getNonPersonalThreshold() {
		return nonPersonalThreshold;
	}

	public void setNonPersonalThreshold(long nonPersonalThreshold) {
		this.nonPersonalThreshold = nonPersonalThreshold;
	}
}
