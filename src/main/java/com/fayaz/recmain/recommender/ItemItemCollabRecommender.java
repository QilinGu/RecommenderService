package com.fayaz.recmain.recommender;

import java.util.List;

import com.fayaz.recmain.rest.pojo.RecommendationItem;

public class ItemItemCollabRecommender implements BaseRecommender {

	@Override
	public List<RecommendationItem> getRecommendations() {
		System.out.println("Item Item");
		return null;
	}

}
