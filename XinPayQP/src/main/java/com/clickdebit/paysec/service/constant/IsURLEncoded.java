package com.clickdebit.paysec.service.constant;

public enum IsURLEncoded {

	NO(0),
	YES(1),
	;
	
	private Integer value;
	
	private IsURLEncoded(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
