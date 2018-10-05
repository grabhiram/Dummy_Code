package com.clickdebit.paysec.connector.service;

import java.util.Map;

import com.clickdebit.exception.EmulationException;

public interface IConnectorSignature {
	
	String DELIMITER_COMMA = ",";
	String DELIMITER_SEMI_COLON = ";";
	String DELIMITER_PIPE = "|";
	
/*	public String generateTokenRequestSignature(Map<String, String> paramMap) throws EmulationException;
*/	
	public String generatePaymentRequestSignature(Map<String, String> paramMap) throws EmulationException;
	
	/*public String generateQueryRequestSignature(Map<String, String> paramMap) throws EmulationException;
	
	public String generateQueryResponseSignature(Map<String, String> paramMap) throws EmulationException;
	
	public String generateCallbackRequestSignature(Map<String, String> paramMap) throws EmulationException;
	
	public String generateReturnSignature(Map<String, String> paramMap) throws EmulationException;*/
	
}
