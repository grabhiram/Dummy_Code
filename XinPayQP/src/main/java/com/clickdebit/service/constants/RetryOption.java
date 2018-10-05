package com.clickdebit.service.constants;

public enum RetryOption {

	CD_RETRY(1),
	BANK_CONNECTOR_RETRY(2),
	;
	
	private int value;
	
	private RetryOption(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
