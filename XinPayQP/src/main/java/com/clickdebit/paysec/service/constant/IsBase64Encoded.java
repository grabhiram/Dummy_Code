package com.clickdebit.paysec.service.constant;

public enum IsBase64Encoded {

	NO(0),
	YES(1),
	;
	
	private Integer value;
	
	private IsBase64Encoded(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
