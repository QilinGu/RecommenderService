package com.fayaz.recmain.recommender;

import java.util.List;
import com.fayaz.recmain.rest.pojo.RecommendationItem;

public interface BaseRecommender {
	
	public List<RecommendationItem> getRecommendations();

}
