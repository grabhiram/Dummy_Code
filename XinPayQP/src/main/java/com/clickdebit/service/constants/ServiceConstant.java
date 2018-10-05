package com.clickdebit.service.constants;

public enum ServiceConstant {
	HEADER_KEY_NAME("header"), SUCCESS_PAGE("success.page"), FAILURE_PAGE("failure.page");
	
	private String value;
	
	private ServiceConstant(String key) {
		this.value = key;
	}

	public String getKey() {
		return value;
	}

}
