package com.fayaz.recmain.recommender.deepconfig;

import java.util.concurrent.ConcurrentHashMap;



public class TokenCache {
	
	private static ConcurrentHashMap<String, Long> tokenCustomerMap
		 = new ConcurrentHashMap<String, Long>();
	
	public static void putToCache(String key,Long value){
		
		if(tokenCustomerMap.get(key)!=null)
			throw new RuntimeException("Token Cache Error: Key already exists");
		tokenCustomerMap.put(key, value);
		
	}
	
	public static Long getFromCache(String key){
		return tokenCustomerMap.get(key);
	}

}
