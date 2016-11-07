package com.fayaz.recmain.recommender.rest.pojo;

public class Config {
	
	private long algorithmId;
	private long noOfRecommendations;
	private long minRatings;
	private float hybridUser;
	private float hybridItem;
	private float hybridContent;
	
	public Config(){}
	
	public long getAlgorithmId() {
		return algorithmId;
	}
	public void setAlgorithmId(long algorithmId) {
		this.algorithmId = algorithmId;
	}
	public long getNoOfRecommendations() {
		return noOfRecommendations;
	}
	public void setNoOfRecommendations(long noOfRecommendations) {
		this.noOfRecommendations = noOfRecommendations;
	}
	public long getMinRatings() {
		return minRatings;
	}
	public void setMinRatings(long minRatings) {
		this.minRatings = minRatings;
	}
	public float getHybridUser() {
		return hybridUser;
	}
	public void setHybridUser(float hybridUser) {
		this.hybridUser = hybridUser;
	}
	public float getHybridItem() {
		return hybridItem;
	}
	public void setHybridItem(float hybridItem) {
		this.hybridItem = hybridItem;
	}
	public float getHybridContent() {
		return hybridContent;
	}
	public void setHybridContent(float hybridContent) {
		this.hybridContent = hybridContent;
	}
	
	

}
