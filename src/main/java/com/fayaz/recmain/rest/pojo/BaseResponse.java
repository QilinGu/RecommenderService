package com.fayaz.recmain.rest.pojo;

public class BaseResponse {
	
	private String status;
	private String statusMessage;
	
	public BaseResponse(){}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
}
