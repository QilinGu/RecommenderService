package com.fayaz.recmain.rest.pojo;

public class RecommendationItem {
	
	private long userId;
	private long productId;
	private float score;
	
	public RecommendationItem(){}
	
	public RecommendationItem(long userId,long productId,float score){
		this.userId = userId;
		this.productId = productId;
		this.score = score;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
}
