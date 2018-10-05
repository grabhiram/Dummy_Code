package com.clickdebit.paysec.service.constant;

public enum PayInTransactionEventStatus {

	CREATED(1),
	TOKEN_RECEIVED(2),
	SENT_TO_CONNECTOR(3),
	CONNECTOR_RESPONSE_PROCESSED(4),
	CALLBACK_SENT_TO_MERCHANT(5);
	
	private int value;
	
	private PayInTransactionEventStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
