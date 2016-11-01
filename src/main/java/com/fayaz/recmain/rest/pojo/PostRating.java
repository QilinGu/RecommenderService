package com.fayaz.recmain.rest.pojo;

import java.util.List;

public class PostRating extends RatingBaseRequest{
	
	private List<RatingItem> ratings;	
	
	public PostRating(){}
	
	public PostRating(long customerId,String customerSecret,List<RatingItem> items){
		super(customerId,customerSecret);		
		this.ratings = items;
	}
	
	public List<RatingItem> getRatings() {
		return ratings;
	}

	public void setRatings(List<RatingItem> ratings) {
		this.ratings = ratings;
	}
}
