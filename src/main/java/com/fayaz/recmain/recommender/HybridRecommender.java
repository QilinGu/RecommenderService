package com.fayaz.recmain.recommender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.fayaz.recmain.recommender.deepconfig.CustomerDeepConfig;
import com.fayaz.recmain.recommender.hibernate.dao.CustomerConfigDAO;
import com.fayaz.recmain.recommender.hibernate.pojo.CustomerConfig;
import com.fayaz.recmain.recommender.rest.pojo.RecommendationItem;
import com.fayaz.recmain.recommender.similarity.ContentSimilarity;

public class HybridRecommender extends BaseRecommender {

	@Override
	public List<RecommendationItem> getRecommendations(long userId,
			long customerId, int count) {
		List<RecommendationItem> returnRecommendationList = new ArrayList<RecommendationItem>();
		try {
			ReloadFromJDBCDataModel dataModel = getDataModel(customerId);
			CustomerDeepConfig deepConfig = new CustomerDeepConfig(customerId);
			CustomerConfigDAO customerConfig = new CustomerConfigDAO();
			CustomerConfig config = customerConfig
					.getConfigByCustomerId(customerId);
			final CountDownLatch latch = new CountDownLatch(3);
			float userWeightage = config.getHybridUserPerc();
			float itemWeightage = config.getHybridItemPerc();
			float contentWeightage = config.getHybridContentPerc();
			//calculate user based recommendations
			Thread userThread = new Thread(getUserBasedRecommendationThread(latch,dataModel, deepConfig, userId, count, returnRecommendationList, userWeightage));
			userThread.start();
			//calculate item based recommendations
			Thread itemThread = new Thread(getItemBasedRecommendationThread(latch,dataModel, deepConfig, userId, count, returnRecommendationList, itemWeightage));
			itemThread.run();
			//calculate content based recommendations
			Thread contentThread = new Thread(getContentBasedRecommendationThread(latch,dataModel, deepConfig, userId, count, customerId, returnRecommendationList, contentWeightage));
			contentThread.run();
			System.out.println("Waiting1");
			latch.await();//wait to finish all calculations
			System.out.println(returnRecommendationList.size());
			Iterator<RecommendationItem> iter = returnRecommendationList.iterator();
			HashMap<Long,RecommendationItem> idToScoreMap = new HashMap<Long,RecommendationItem>();
			while(iter.hasNext()){
				RecommendationItem item = iter.next();
				if(idToScoreMap.get(item.getProductId())!=null){
					RecommendationItem itemInMap = idToScoreMap.get(item.getProductId());
					item.setScore(itemInMap.getScore()+item.getScore());
				}
				idToScoreMap.put(item.getProductId(), item);
			}
			returnRecommendationList = new ArrayList<RecommendationItem>(idToScoreMap.values());
			Collections.sort(returnRecommendationList, new Comparator<RecommendationItem>() {

				@Override
				public int compare(RecommendationItem o1, RecommendationItem o2) {
					if(o1.getScore()>o2.getScore())
						return -1;
					else if(o1.getScore()<o2.getScore())
						return 1;
					else 
						return 0;
				}
			});
			returnRecommendationList = returnRecommendationList.subList(0, count-1);

		} catch (TasteException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return returnRecommendationList;
	}

	private Runnable getUserBasedRecommendationThread(final CountDownLatch latch ,
			final DataModel dataModel, final CustomerDeepConfig config,
			final long userId, final int count,
			final List<RecommendationItem> recommendList, final float weightage) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("user user");
					UserSimilarity similarity = config
							.getUserSimilarityMeasure(dataModel);
					UserNeighborhood neighborhood = config
							.getThresholduserNeighbourhood(similarity,
									dataModel);
					UserBasedRecommender recommender = config
							.getUserRecommender(dataModel, neighborhood,
									similarity);
					List<RecommendedItem> recommendations = recommender
							.recommend(userId, count - 1);
					List<RecommendationItem> tempRecommendList = new ArrayList<RecommendationItem>();
					for (RecommendedItem recommendation : recommendations) {
						RecommendationItem item = new RecommendationItem(
								recommendation.getItemID(),
								(recommendation.getValue() * weightage));
						tempRecommendList.add(item);
					}
					synchronized (recommendList) {
						recommendList.addAll(tempRecommendList);
					}
				} catch (TasteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					latch.countDown();
				}
			}
		};
	}

	private Runnable getItemBasedRecommendationThread(final CountDownLatch latch ,
			final DataModel dataModel, final CustomerDeepConfig config,
			final long userId, final int count,
			final List<RecommendationItem> recommendList, final float weightage) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("item item");
					ItemSimilarity similarity = config
							.getItemSimilarityMeasure(dataModel);
					ItemBasedRecommender recommender = config
							.getItemRecommender(dataModel, similarity);
					List<RecommendedItem> recommendations = recommender
							.recommend(userId, count - 1);
					List<RecommendationItem> tempRecommendList = new ArrayList<RecommendationItem>();
					for (RecommendedItem recommendation : recommendations) {
						RecommendationItem item = new RecommendationItem(
								recommendation.getItemID(),
								(recommendation.getValue() * weightage));
						tempRecommendList.add(item);
					}
					synchronized (recommendList) {
						recommendList.addAll(tempRecommendList);
					}
				} catch (TasteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					latch.countDown();
				}
			}
		};
	}

	private Runnable getContentBasedRecommendationThread(final CountDownLatch latch ,
			final DataModel dataModel, final CustomerDeepConfig config,
			final long userId, final int count, final long customerId,
			final List<RecommendationItem> recommendList, final float weightage) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("content based");
					ItemSimilarity similarity = new ContentSimilarity(
							customerId, dataModel);
					ItemBasedRecommender recommender = config
							.getItemRecommender(dataModel, similarity);
					List<RecommendedItem> recommendations = recommender
							.recommend(userId, count - 1);
					List<RecommendationItem> tempRecommendList = new ArrayList<RecommendationItem>();
					for (RecommendedItem recommendation : recommendations) {
						RecommendationItem item = new RecommendationItem(
								recommendation.getItemID(),
								(recommendation.getValue() * weightage));
						tempRecommendList.add(item);
					}
					synchronized (recommendList) {
						recommendList.addAll(tempRecommendList);
					}
				} catch (TasteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					latch.countDown();
				}
			}
		};
	}

}
