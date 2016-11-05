package com.fayaz.recmain.recommender;

import java.util.LinkedList;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import com.fayaz.recmain.recommender.deepconfig.CustomerDeepConfig;
import com.fayaz.recmain.recommender.rest.pojo.RecommendationItem;

public class ItemItemCollabRecommender extends BaseRecommender {

	@Override
	public List<RecommendationItem> getRecommendations(long userId,long customerId,int count) {
		List<RecommendationItem> returnRecommendationList = null;
		try {
			ReloadFromJDBCDataModel dataModel = getDataModel(customerId);
			CustomerDeepConfig config = new CustomerDeepConfig(customerId);
			ItemSimilarity similarity= config.getItemSimilarityMeasure(dataModel);
			ItemBasedRecommender recommender = config.getItemRecommender(dataModel,similarity);
			List<RecommendedItem> recommendations = recommender.recommend(userId, count-1);
			returnRecommendationList = new LinkedList<RecommendationItem>();
			for (RecommendedItem recommendation : recommendations) {				
				RecommendationItem item = new RecommendationItem(recommendation.getItemID(),recommendation.getValue());
				returnRecommendationList.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
		return returnRecommendationList;
	}
}
