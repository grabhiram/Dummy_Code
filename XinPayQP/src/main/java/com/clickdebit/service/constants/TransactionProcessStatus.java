package com.clickdebit.service.constants;

public enum TransactionProcessStatus {

	IN_PROGRESS(-1),
	SUCCESS(0),
	FAILURE(99);
	
	private int value;
	
	private TransactionProcessStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
