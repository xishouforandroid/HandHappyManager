package com.liangxunwang.unimanager.baiduyun.model;

public class HttpRestResponse {
	
	private int httpStatusCode;

	private String jsonResponse;

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getJsonResponse() {
		return jsonResponse;
	}

	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}
	
}
