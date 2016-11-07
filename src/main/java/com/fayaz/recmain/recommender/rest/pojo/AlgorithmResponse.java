package com.fayaz.recmain.recommender.rest.pojo;

import java.util.List;

public class AlgorithmResponse extends BaseResponse{
	private List<AlgorithmItem> items;
	public AlgorithmResponse(){	}


	public List<AlgorithmItem> getItems() {
		return items;
	}

	public void setItems(List<AlgorithmItem> items) {
		this.items = items;
	}	
}
