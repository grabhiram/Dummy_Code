package com.clickdebit.paysec.service.constant;

public enum PayInTransactionStatus {

	IN_PROGRESS(-1),
	SUCCESS(0),
	REFUND(66),
	FAILED(99),
	;
	
	private Integer value;
	
	private PayInTransactionStatus(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
