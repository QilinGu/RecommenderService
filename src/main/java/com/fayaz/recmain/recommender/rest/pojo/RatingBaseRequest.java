package com.fayaz.recmain.recommender.rest.pojo;

public class RatingBaseRequest {
	private long customerId;
	private String customerSecret;
	
	public RatingBaseRequest(){}
	
	public RatingBaseRequest(long customerId,String customerSecret){
		this.customerId = customerId;
		this.customerSecret = customerSecret;
	}
	
	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerSecret() {
		return customerSecret;
	}

	public void setCustomerSecret(String customerSecret) {
		this.customerSecret = customerSecret;
	}
}
