package com.clickdebit.paysec.service.constant;

public enum ParentParam {
	
	NO(0),
	YES(1),
	;
	
	private Integer value;
	
	private ParentParam(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}


}
