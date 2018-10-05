package com.clickdebit.service.constants;

public enum FeatureEnum {
	
	BANK_CONNECTOR_RETRY("BANK_CONNECTOR_RETRY"),
	PAY_IN("PAY_IN"),
	PAY_OUT("PAY_OUT");
	
	private String value;
	
	private FeatureEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}


}
