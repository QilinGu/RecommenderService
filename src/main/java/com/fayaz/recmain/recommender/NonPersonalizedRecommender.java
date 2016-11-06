package com.fayaz.recmain.recommender;

import java.util.List;

import com.fayaz.recmain.recommender.hibernate.dao.RatingsDAO;
import com.fayaz.recmain.recommender.rest.pojo.RecommendationItem;

public class NonPersonalizedRecommender extends BaseRecommender{

	@Override
	public List<RecommendationItem> getRecommendations(long userId,long customerId,int count) {
		//userId insignificant here
		//we are providing  non personalized recommendations
		RatingsDAO dao = new RatingsDAO();
		return dao.getTopRatedProducts(customerId, 10, count);
	}

}
