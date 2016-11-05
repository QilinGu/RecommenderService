package com.fayaz.recmain.recommender.rest.pojo;

public class RatingItem {
	
	private long userId;
	private long productId;
	private long rating;
	
	public RatingItem(){}
	
	public RatingItem(long userId,long productId,long rating){
		this.userId = userId;
		this.productId = productId;
		this.rating = rating;
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
	
	public long getRating() {
		return rating;
	}
	public void setRating(long rating) {
		this.rating = rating;
	}	

}
