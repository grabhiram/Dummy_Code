package com.clickdebit.paysec.service.constant;

public enum InternalParam {

	NO(0),
	YES(1),
	;
	
	private Integer value;
	
	private InternalParam(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
