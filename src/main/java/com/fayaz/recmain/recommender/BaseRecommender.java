package com.fayaz.recmain.recommender;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;

import com.fayaz.recmain.recommender.datamodel.OracleJDBCDataModel;
import com.fayaz.recmain.rest.pojo.RecommendationItem;

public abstract class BaseRecommender {
	
	private static ConcurrentHashMap<Long,ReloadFromJDBCDataModel> customerToModelMap = new ConcurrentHashMap<Long,ReloadFromJDBCDataModel>();
	
	public abstract List<RecommendationItem> getRecommendations(long userId,long customerId,int count);
	
	public ReloadFromJDBCDataModel getDataModel(long customerId) throws TasteException{
		if(customerToModelMap.get(customerId)!=null){
			return customerToModelMap.get(customerId);
		}else{
			JDBCDataModel model = new OracleJDBCDataModel(customerId);
			ReloadFromJDBCDataModel dataModel = new ReloadFromJDBCDataModel(model);
			customerToModelMap.put(customerId, dataModel);
			return dataModel;
		}		
	}

}
