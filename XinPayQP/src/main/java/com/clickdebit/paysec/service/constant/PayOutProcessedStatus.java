package com.clickdebit.paysec.service.constant;

public enum PayOutProcessedStatus {

	PENDING(99),
	IN_PROGRESS(-1),
	COMPLETED(0);
	
	private Integer value;
	
	private PayOutProcessedStatus(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
