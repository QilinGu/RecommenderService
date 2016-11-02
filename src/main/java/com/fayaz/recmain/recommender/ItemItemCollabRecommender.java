package com.fayaz.recmain.recommender;

import java.util.List;

import com.fayaz.recmain.rest.pojo.RecommendationItem;

public class ItemItemCollabRecommender extends BaseRecommender {

	@Override
	public List<RecommendationItem> getRecommendations(long userId,long customerId,int count) {
		System.out.println("Item Item");
		return null;
	}

}
