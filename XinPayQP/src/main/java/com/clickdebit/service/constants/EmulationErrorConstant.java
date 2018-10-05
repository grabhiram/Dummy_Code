package com.clickdebit.service.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EmulationErrorConstant {
	
//	  UNSUPPORTED_BANK(100, "The bank you chose is not supported. Please choose another bank."),
	  BANK_LOGIN_PAGE_LOAD_FAILURE(101, "Unable to Load Bank Login Page"),
	  LOGIN_FAILURE(102, "Invalid Login Credentials"),
	  TRANSACTION_INITIATION_FAILURE(103, "Failed to Initiate transfer on bank site"),
	  INSUFFICIENT_FUND(104, "Insufficient Fund"),
	  NO_MOBILE_NUMBER(105, "No Mobile Number"),
//	  DAILY_AMOUNT_LIMIT_EXCEEDED(106, "You have exceeded the daily transaction limit. Please use another bank or try again after 24 hours."),
//	  WEEKLY_AMOUNT_LIMIT_EXCEEDED(107, "Oops. Something has gone wrong.  Please wait 5 mins before attempting another transaction. If you are still unable to proceed please retry after an hour or use another payment method."),
//	  MONTHLY_AMOUNT_LIMIT_EXCEEDED(108, "Oops. Something has gone wrong.  Please wait 5 mins before attempting another transaction. If you are still unable to proceed please retry after an hour or use another payment method."),
//	  DUPLICATE_TRANSACTION_ERROR(109, "A previous transaction with the same details has been recently submitted. This can occur when you refresh the payment page or click on the login button more than once. Please go back to the merchant page to complete the transaction."),
	  INVALID_PAYEE_OTP(110, "Invalid Payee OTP"),
	  INVALID_TRANSACTION_OTP(111, "Invalid Transaction OTP"),
	  BAD_REQUEST(112, "Wrong data sent via API"),
	  SESSION_EXPIRED(113, "Session Expired"),
//	  DB_ERROR(114, "Not able to communicate with CD DB"),
	  DB_ERROR(114, "Something went wrong. Please report your transaction to support and try after sometime."),
	  
	  ELEMENT_NOT_FOUND(115, "Change in Page content on bank site"),
	  INTERNAL_ERROR(116, "Internal Error"),
	  ADD_PAYEE_PAGE_LOAD_FAILURE(117, "Not able to load Add Payee page on the Bank site"),
//	  PAYEE_EXIST_PAGE_LOAD_FAILURE(118, "Not able to validate Payee data on Bank site"),
	  PAYEE_OTP_SUBMIT_PAGE_LOAD_FAILURE(119, "Add Payee OTP page not displayed on the Bank site"),
	  ADD_PAYEE_SERVICE_UNAVAILABLE(120, "Add Payee Service Unavailable on Bank site"),
	  LOGIN_OTP_FAILURE(121, "Invalid Login OTP"),
	  LOGIN_CAPTCHA_FAILURE(122, "Invalid Login Captcha"),
//	  DAILY_BANK_AMOUNT_LIMIT_EXCEEDED(123, "You have exceeded the daily transaction limit. Please use another bank or try again after 24 hours."),
//	  NO_SUPPORTED_BANK_FOUND(124, "The bank you chose is not supported. Please choose another bank."),
//	  DOES_PAYEE_EXIST(124, "Bank not supported on CD Platform"),
	  NO_SUPPORTED_BANK_FOUND(124, "Bank not supported on CD Platform"),
	  TXN_OTP_SUCCESS_MESSAGE_FAILURE(125, "Final Transaction status message changed on bank site"),
	  NO_FROM_BANK_FOUND(126, "No From Bank Found"),
	  
	  FIRST_TIME_USER_TERMS_CONDITIONS(127, "Unagreed First Time User Terms and Conditions"),
	  BANK_SERVICE_UNAVAILABLE(128,"Bank Service Unavailable"),
	  
	  MAX_PAYEE_LIMIT_REACHED(130,"Maximum Payee Add Limit Reached"),
	  BANK_ACCOUNT_LOCKED(131,"Bank Account is Locked"),
	  MAX_BANK_LIMIT_AMOUNT_EXCEEDED(132,"Maximum Bank Transaction Amount Limit Exceeded"),
	  NET_BANKING_ACTIVATION_REQUIRED(133,"Net Banking Activation Required"),
	  INVALID_BSB_NUMBER(134,"Invalid BSB Number"),
	  INVALID_ANSWER_FOR_SECURITY_QUESTION(135,"Invalid Answer for Security Question"),
	  BROWSER_BACK_BUTTON_CLICKED(136,"Browser back button clicked during In-Progress Transaction"),
//	  MOBILE_NUMBER_NOT_REGISTERED(137,"Mobile number is not register on Bank Site"),
	  
	  USER_INITIATED_CANCEL(199, "User Initiated Cancel"),
	  PAGE_VERIFICATION_FAILURE(200, "New Page displayed on Bank site"),
//	  EMPTY_BANK_NAME(201, "Currently we are facing some problem, Please try again after some time."),

	  
	  RECURRING_BAD_REQUEST(202, "Invalid Recurring Details"),
	  OTP_EXPIRED(203, "OTP got Expired"),
	  NO_OTP_FOR_AUTOMATED_PAYOUT(204, "No OTP received for automated payout"),
	  
	  TRANSACTION_CUT_OFF_TIME_EXCEEDED(205, "Transaction time has exceeded the cut-off time. Your transaction cannot be processed now.  Please try again in working hours."),
	  
	  WAITING_FOR_ADD_PAYEE_COFIRMATION(400, "Payee has to be confirmed with OTP by user manually"),
	  MINIMUM_TRANSACTION_AMOUNT_REQUIRED(401, "Mininum transaction amount required"),

	  DUPLICATE_TRANSACTION(500, "Duplicate Transaction"),
	  INVALID_MERCHANT_ID(501, "Invalid Merchant Id"),
	  SYSTEM_INITIATED_TIMEOUT(502, "Login Data/OTP not entered by Customer"),
	  INVALID_CURRENCY_FOR_MERCHANT(503, "Invalid Currency for Merchant"),
	  NO_MERCHANT_PAYOUT_ACCOUNT_FOUND(504, "No Merchant Payout Account Found"),
	  INVALID_SIGNATURE(505, "Invalid Signature"),

	  /*
	   * Paysec Error codes
	   * */
	  DATA_CONFIGURATION_ISSUE(1001, "Connector configuration issue"),
	  SIGNATURE_PARSE_ISSUE(1002, "Signature parsing issue"),
	  INVALID_REQUEST(1003, "Invalid request"),
	  CONNECTOR_PAYMENT_REQUEST_FAILURE(1004, "Connector configuration issue"),
	  CONNECTOR_QUERY_STATUS_REQUEST_FAILURE(1005, "Connector configuration issue"),
	  INVALID_QUERY_STATUS_SIGNATURE(1006, "Connector configuration issue"),
	  CONNECTOR_STATUS_MAP_NOT_FOUND(1007, "Connector configuration issue"),
	  INVALID_MERCHANT(1008, "Invalid merchant"),
	  INVALID_CHANNEL(1009, "Invalid channel"),
	  INVALID_ORDER_AMOUNT(1010, "Invalid order amount"),
//	  NO_CONNECTOR_AVAILABLE(1011, "No connector available for processing"),
	  NO_CONNECTOR_AVAILABLE(1011, "Unable to process your request"),
	  REQUEST_TOKEN_ALREADY_CONSUMED(1012, "Request token already consumed"),
	  MINIMUM_CURRENCY_AMOUNT_REQUIRED(1013, "Minimum amount {0} required for currency {1}"),
	  REQUEST_TOKEN_MANDATORY_PARAMS_NOT_PRESENT(1014, "Request token mandatory paramsters not present"),
	  PLATFORM_INTERNAL_ERROR(1015, "Something went wrong. Please report your transaction to support and try after sometime."),
	  SEND_TOKEN_MANDATORY_PARAMS_NOT_PRESENT(1016, "Send token mandatory paramsters not present"),
	  GET_QUERY_STATUS_MANDATORY_PARAMS_NOT_PRESENT(1017, "Get query status mandatory paramsters not present"),
	  BANK_SELECTION_MANDATORY_PARAMS_NOT_PRESENT(1018, "Bank selection mandatory paramsters not present"),
	  GET_REDIRECT_RESPONSE_MANDATORY_PARAMS_NOT_PRESENT(1019, "Redirect response mandatory paramsters not present"),
	  INVALID_TOKEN(1020, "Invalid token"),
	  ORDER_AMOUNT_FORMAT_MISMATCH(1021, "Order amount should have 2 decimal places"),
	  
//	  LOW_MERCANT_BALANCE(1022, "Current merchant account balance is low"),
//	  LOW_MERCANT_BALANCE(1022, "Amount requested {0} is higher than the current Running Balance {1}"),
	  LOW_MERCANT_BALANCE(1022, "Amount requested {0} is not within Available Running Balance"),
	  INVALID_MERCHANT_PAYOUT_SLAB(1023, "Unable to find merchant payout slab"),
	  SETTLEMENT_ENTRY_NOT_FOUND(1024, "Unable to find settlement entry"),
	  PAYOUT_MIN_MAX_AMOUNT_LIMIT_ERROR(1025, "Payout Min/Max amount limit error"),
	  INVALID_MERCHANT_ENDPOINT_IP_ADDRESS(1026, "Invalid merchant endpoint IP address"),
	  
	  NO_CALLBACK_URL_FOUND(1027, "No callback URL found"),
	  REDIRECT_FAILURE(1028, "Unable to redirect to merchant site"),

	  RETRY_QR_CODE_EXTRACTION(1029, "Retrying QR Code extraction with another connector"),
	  CONNECTOR_PARAM_VALUE_MISMATCH(1030, "Original transaction param not matching with connector param"),

	  PAYSEC_TRANSACTION_TIMEOUT(9999, "Marked as timeout due to transaction inactivity"),
	  
	  ;
	
	private int value;
	private String message;

	private static Map<Integer, EmulationErrorConstant> map = new HashMap<Integer, EmulationErrorConstant>();

    static {
        for (EmulationErrorConstant ee : EmulationErrorConstant.values()) {
            map.put(ee.value, ee);
        }
    }
    
    public static EmulationErrorConstant valueOf(int value) {
        return map.get(value);
    }
	
	private EmulationErrorConstant(int value, String message) {
		this.value = value;
		this.message = message;
	}

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return message;
	}
	
	public String getDynamicMessage(List<String> values) {
		if(values == null || values.size() <= 0) {
			return message;
		}
		String newMessage = message;
		for(int i = 0; i < values.size(); i++) {
			newMessage = newMessage.replace("{"+i+"}", values.get(i));
		}
		return newMessage;
	}
	
}
