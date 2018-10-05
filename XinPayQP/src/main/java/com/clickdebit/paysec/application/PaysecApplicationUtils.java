package com.clickdebit.paysec.application;

import java.text.DecimalFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clickdebit.application.util.GenerateRandom;

public class PaysecApplicationUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(PaysecApplicationUtils.class);

	public static final String SUCCESS_STATUS = "SUCCESS";
	public static final String FAILURE_STATUS = "FAILURE";
	public static final String PENDING_STATUS = "PENDING";
	public static final String QUERY_STATUS_SIGNATURE_DELIMITER = ";";
	public static final String REDIRECT_CALLBACK_ORDER_AMOUNT_FORMAT = "#.00";
	public static final String REQUEST_TOKEN_ORDER_AMOUNT_FORMAT = "#.00";
	
	public static String generateQueryStatusSignature(List<String> signatureValues) {
		if(signatureValues == null || signatureValues.size() <= 0) {
			logger.info("Invalid params for signature");
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		for(String s:signatureValues) {
			sb.append(s);
			sb.append(QUERY_STATUS_SIGNATURE_DELIMITER);
		}
		String plainText = sb.toString();
		return plainText.substring(0, plainText.length() - 1);
	}

	public static String generateToken() {
		return GenerateRandom.randomAlphaNumeric(64);
	}

	public static String generateOrderReference(String merchantCode) {
		String orderReferenceNumber = GenerateRandom.randomAlphaNumeric(25);
//		UUID u = UUID.randomUUID();
//		String orderReferenceNumber = u.toString();
//		orderReferenceNumber.replace("-", "_");
		return orderReferenceNumber;
	}
	
	public static String getDerivedAmount(double doubleVal) {
		DecimalFormat df = new DecimalFormat("#.00");
	    String strDblVal = df.format(doubleVal);
	    String newVal = strDblVal;
	    
	   logger.info("new value for amount is : " + newVal);
		
	   return newVal;
		 
	}


}
