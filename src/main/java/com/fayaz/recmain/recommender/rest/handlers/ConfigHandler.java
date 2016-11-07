package com.fayaz.recmain.recommender.rest.handlers;

import com.fayaz.recmain.recommender.deepconfig.TokenCache;
import com.fayaz.recmain.recommender.hibernate.dao.CustomerConfigDAO;
import com.fayaz.recmain.recommender.hibernate.pojo.CustomerConfig;
import com.fayaz.recmain.recommender.rest.pojo.Config;

public class ConfigHandler {

	public Config getConfig(String secret) throws RuntimeException{
		Long customerId = TokenCache.getFromCache(secret);
		if(customerId==null)
			throw new RuntimeException("Corrupt Request..Invalid client Token");
		
		CustomerConfigDAO dao = new CustomerConfigDAO();
		CustomerConfig configDB= dao.getConfigByCustomerId(customerId);
		if(configDB==null)
			throw new RuntimeException("Customer Configuration yet to be done");
		Config configResponseItem = new Config();
		configResponseItem.setAlgorithmId(configDB.getAlgoId());
		configResponseItem.setMinRatings(configDB.getNonPersonalThreshold());
		configResponseItem.setNoOfRecommendations(configDB.getReturnCount());
		configResponseItem.setHybridUser(configDB.getHybridUserPerc());
		configResponseItem.setHybridItem(configDB.getHybridItemPerc());
		configResponseItem.setHybridContent(configDB.getHybridContentPerc());
		return configResponseItem;
	}

	public String updateConfig(String secret, Config config) throws RuntimeException{
		Long customerId = TokenCache.getFromCache(secret);
		String returnMsg;
		boolean add = false;
		if(customerId==null)
			throw new RuntimeException("Corrupt Request..Invalid client Token");
		CustomerConfigDAO dao = new CustomerConfigDAO();
		CustomerConfig configDB= dao.getConfigByCustomerId(customerId);
		
		if(configDB==null){
			returnMsg = "Added customer config data";
			add=true;
			configDB= new CustomerConfig();
			
		}else{
			returnMsg = "Updated customer config data";
		}
		configDB.setAlgoId(config.getAlgorithmId());
		configDB.setCustomerId(customerId);
		configDB.setNonPersonalThreshold(config.getMinRatings());
		configDB.setReturnCount(config.getNoOfRecommendations());
		configDB.setHybridUserPerc(config.getHybridUser());
		configDB.setHybridItemPerc(config.getHybridItem());
		configDB.setHybridContentPerc(config.getHybridContent());	
		if(add)
			dao.addCustomerConfig(configDB);
		else
			dao.updateCustomerConfig(configDB);		
		return returnMsg;
	}

}
