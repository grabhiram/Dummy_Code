package com.clickdebit.paysec.service.constant;

public enum SuspiciousActivity {

	QUERY_STATUS_MISMATCH(1),
	CALLBACK_STATUS_MISMATCH(2),
	REDIRECT_STATUS_MISMATCH(3),
	CALLBACK_RESPONSE_SIGNATURE_MISMATCH(4),
	REDIRECT_RESPONSE_SIGNATURE_MISMATCH(5),
	QUERY_RESPONSE_SIGNATURE_MISMATCH(6),
	;
	
	private Integer value;
	
	private SuspiciousActivity(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
