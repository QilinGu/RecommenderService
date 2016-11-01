package com.fayaz.recmain.rest.pojo;

import java.util.List;

public class RecommendationResponse extends BaseResponse {
	
	private List<RecommendationItem> recommendationList;
	
	public RecommendationResponse(){}
	
	public RecommendationResponse(String status,String message,List<RecommendationItem> recommendationList){
		super(status,message);
		this.recommendationList = recommendationList;
	}

	public List<RecommendationItem> getRecommendationList() {
		return recommendationList;
	}

	public void setRecommendationList(List<RecommendationItem> recommendationList) {
		this.recommendationList = recommendationList;
	}
	
	
	
	

}
