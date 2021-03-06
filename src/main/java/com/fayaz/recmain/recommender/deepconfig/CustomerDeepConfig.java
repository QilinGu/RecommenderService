package com.fayaz.recmain.recommender.deepconfig;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class CustomerDeepConfig {
	
	private long customerId;
	
	private static final double USER_THRESHOLD_NEIGHBOURHOOD= 0.1;
	
	public CustomerDeepConfig(){}
	
	public CustomerDeepConfig(long customerId){
		this.customerId = customerId;
	}
	
	public UserSimilarity getUserSimilarityMeasure(DataModel model) throws TasteException{
		return new PearsonCorrelationSimilarity(model);
	}
	
	public ItemSimilarity getItemSimilarityMeasure(DataModel model) throws TasteException{
		return new PearsonCorrelationSimilarity(model);
	}

	public UserNeighborhood getThresholduserNeighbourhood(
			UserSimilarity similarity, DataModel dataModel) {
		return new ThresholdUserNeighborhood(USER_THRESHOLD_NEIGHBOURHOOD, similarity, dataModel);
	}

	public UserBasedRecommender getUserRecommender(DataModel dataModel,
			UserNeighborhood neighborhood, UserSimilarity similarity) {		
		return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
	}
	
	public ItemBasedRecommender getItemRecommender(DataModel dataModel,ItemSimilarity similarity) {		
		return new GenericItemBasedRecommender(dataModel, similarity);
	}
}
