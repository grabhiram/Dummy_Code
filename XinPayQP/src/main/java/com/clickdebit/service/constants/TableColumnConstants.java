package com.clickdebit.service.constants;

public enum TableColumnConstants {
	TABLE_SEPARATOR("->"),

	MERCHANT_SUPPORTED_BANK_TABLE_NAME("MerchantSupportedBank"),

	BANK_MASTER_TABLE_NAME("BankMaster"),
		BANK_CODE_COLUMN_NAME("BANK_CODE"),

	MERCHANT_ACCOUNT_TABLE_NAME("MerchantAccount"),
		PAYEE_NICKNAME_COLUMN_NAME("PAYEE_NICKNAME"),
		TO_ACCOUNT_NUMBER_COLUMN_NAME("TO_ACCOUNT_NUMBER"),
		BSB_NUMBER_COLUMN_NAME("BSB_NUMBER"),

	EMULATION_STEP_TABLE_NAME("EmulationStep"),
		EMULATION_STEP_NAME_COLUMN_NAME("emulationStepName"),

	TRANSACTION_TABLE_NAME("Transaction"),
		UUID_COLUMN_NAME("UU_ID"),
		TRANSACTION_ID_COLUMN_NAME("transactionId"),
		FIRST_NAME_COLUMN_NAME("firstName"),
		AMOUNT_COLUMN_NAME("AMOUNT"),
		ERROR_STATUS_COLUMN_NAME("ERROR_STATUS"),
		STATUS_COLUMN_NAME("STATUS")
	;
	
	private String value;
	
	private TableColumnConstants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
