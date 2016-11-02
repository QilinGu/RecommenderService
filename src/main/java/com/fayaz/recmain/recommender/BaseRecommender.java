package com.fayaz.recmain.recommender;

import java.util.List;
import com.fayaz.recmain.rest.pojo.RecommendationItem;

public abstract class BaseRecommender {
	
	public abstract List<RecommendationItem> getRecommendations(long userId,long customerId,int count);

}
