package com.fayaz.recmain.recommender.rest.pojo;

public class AlgorithmItem {
	
	private long algoId;
	private String algoName;
	private String algoDesc;
	
	public AlgorithmItem(){
		
	}

	public long getAlgoId() {
		return algoId;
	}

	public void setAlgoId(long algoId) {
		this.algoId = algoId;
	}

	public String getAlgoName() {
		return algoName;
	}

	public void setAlgoName(String algoName) {
		this.algoName = algoName;
	}

	public String getAlgoDesc() {
		return algoDesc;
	}

	public void setAlgoDesc(String algoDesc) {
		this.algoDesc = algoDesc;
	}
	

}
