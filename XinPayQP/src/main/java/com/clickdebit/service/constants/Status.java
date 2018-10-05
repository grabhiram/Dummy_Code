package com.clickdebit.service.constants;

public enum Status {
	
	ACTIVE(1),
	INACTIVE(0);
	
	private int value;
	
	private Status(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
