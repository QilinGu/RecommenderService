package com.fayaz.recmain.recommender.rest.pojo;

public class RecommendationItem {
	private long productId;
	private double score;
	
	public RecommendationItem(){}
	
	public RecommendationItem(long productId,double score){
		this.productId = productId;
		this.score = score;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	@Override
	public String toString(){
		return "[ Product Id :"+this.getProductId()+" , "+"Score :"+this.getScore()+"]";
	}
	
}
