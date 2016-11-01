package com.fayaz.recmain.rest.pojo;

public class RecommendationRequest extends RatingBaseRequest{
	
	private long userId;
	
	public RecommendationRequest(){}
	
	public RecommendationRequest(long customerId,String customerSecret,long userId){
		super(customerId,customerSecret);
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
