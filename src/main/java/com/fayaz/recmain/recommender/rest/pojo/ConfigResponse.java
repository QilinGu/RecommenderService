package com.fayaz.recmain.recommender.rest.pojo;

public class ConfigResponse extends BaseResponse{
	private Config config;
	
	public ConfigResponse(){}
	
	public Config getConfig() {
		return config;
	}
	public void setConfig(Config config) {
		this.config = config;
	}
}
