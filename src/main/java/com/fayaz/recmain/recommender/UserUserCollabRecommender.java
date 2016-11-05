package com.fayaz.recmain.recommender;

import java.util.LinkedList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import com.fayaz.recmain.recommender.deepconfig.CustomerDeepConfig;
import com.fayaz.recmain.recommender.rest.pojo.RecommendationItem;

public class UserUserCollabRecommender extends BaseRecommender{
	
	public UserUserCollabRecommender(){
		System.out.println("New User user collab recommender");
	}

	@Override
	public List<RecommendationItem> getRecommendations(long userId,long customerId,int count) throws RuntimeException{
		List<RecommendationItem> returnRecommendationList = null;
		try {			
			ReloadFromJDBCDataModel dataModel = getDataModel(customerId);
			CustomerDeepConfig config = new CustomerDeepConfig(customerId);
			UserSimilarity similarity = config.getUserSimilarityMeasure(dataModel);
			UserNeighborhood neighborhood = config.getThresholduserNeighbourhood(similarity,dataModel);
			UserBasedRecommender recommender = config.getUserRecommender(dataModel,neighborhood,similarity);
			List<RecommendedItem> recommendations = recommender.recommend(userId, count-1);
			returnRecommendationList = new LinkedList<RecommendationItem>();
			for (RecommendedItem recommendation : recommendations) {				
				RecommendationItem item = new RecommendationItem(recommendation.getItemID(),recommendation.getValue());
				returnRecommendationList.add(item);
			}
		} catch (TasteException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return returnRecommendationList;
	}

}
