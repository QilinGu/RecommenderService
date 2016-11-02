package com.fayaz.recmain.rest.pojo;

public class RecommendationItem {
	private long productId;
	private float score;
	
	public RecommendationItem(){}
	
	public RecommendationItem(long productId,float score){
		this.productId = productId;
		this.score = score;
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
