package com.clickdebit.paysec.connector.service;

import java.util.Map;

import com.clickdebit.exception.EmulationException;

public interface IBaseConnector {

	String FALSE = "false";
	String TRUE = "true";
	String SUCCESS = "SUCCESS";
	String OK = "OK";
		
	Map<String, String> postPaymentRequestForm(Map<String, String> paramMap) throws EmulationException;
	
}
