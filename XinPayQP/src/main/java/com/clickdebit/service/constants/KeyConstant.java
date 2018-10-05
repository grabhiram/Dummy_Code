package com.clickdebit.service.constants;

public enum KeyConstant {
	CONTEXT_PATH("CONTEXT_PATH"),
	
	TRANSACTION_ID("transactionId"),
	
	CONTINUE_TO_NEXT_STEP("CONTINUE_TO_NEXT_STEP"),
	
	TRANSACTION_SUCCESS("TRANSACTION_SUCCESS"),
	TRANSACTION_FAILURE("TRANSACTION_FAILURE"),
	
	SKIP_CHILD_STEPS("SKIP_CHILD_STEPS"),
	STATUS("STATUS"),

	WAP_HTML_PAGE("WAP_HTML_PAGE"),

	SUCCESS("SUCCESS"),
	FAILURE("FAILURE"),
	
	ERROR_CODE("ERROR_CODE"),
	ERROR_MESSAGE("ERROR_MESSAGE"),
	
	RETRY("RETRY"),
	
	YES("YES"),
	NO("NO");
	
	private String value;
	
	private KeyConstant(String key) {
		this.value = key;
	}

	public String getKey() {
		return value;
	}

}
