package com.clickdebit.paysec.service.constant;

public enum IsCupStatus {

	NO(0),
	YES(1),
	;
	
	private Integer value;
	
	private IsCupStatus(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
