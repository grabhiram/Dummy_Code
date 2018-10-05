package com.clickdebit.paysec.service.constant;

public enum SignatureValidationRequired {
	
	NO(0),
	YES_TO_ALL(1),
	ENABLED_FOR_TEST(2),
	;
	
	private Integer value;
	
	private SignatureValidationRequired(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
