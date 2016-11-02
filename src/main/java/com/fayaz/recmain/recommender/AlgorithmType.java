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
	
	public String getName(){
		return this.name;
	}
	
	public static AlgorithmType getAlgorithmTypeInstance(String algoName){
		for(AlgorithmType type:AlgorithmType.values()){
			if(type.getName().equals(algoName))
				return type;
		}		
		throw new IllegalStateException("Invalid algoName "+algoName+"passed");
	}
}
