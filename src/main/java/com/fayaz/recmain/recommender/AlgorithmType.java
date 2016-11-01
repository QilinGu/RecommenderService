package com.fayaz.recmain.recommender;

public enum AlgorithmType {
	
	USER_COLLAB("User-User Collaborative Filtering"),
	ITEM_COLLAB("Item-Item Collaborative Filtering"),
	HYBRID("Hybrid Filtering"),
	CONTENT("Content Based Filtering"),
	NON_PERSONAL("Non-Personalized");
	
	private final String name;
	
	private AlgorithmType(String name){
		this.name = name;
	}
	
	public static AlgorithmType getAlgorithmTypeInstance(String algoName){
		AlgorithmType type1 = AlgorithmType.valueOf(algoName);
		for(AlgorithmType type2:AlgorithmType.values()){
			if(type2.equals(type1))
				return type1;
		}
		throw new IllegalStateException("Invalid algoName passed");
	}
}
