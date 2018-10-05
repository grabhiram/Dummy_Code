package com.clickdebit.paysec.connector.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clickdebit.application.SpringApplicationContext;
import com.clickdebit.exception.EmulationException;
import com.clickdebit.paysec.connector.service.IBaseConnector;
import com.clickdebit.paysec.connector.service.IConnectorSignature;
import com.clickdebit.service.constants.EmulationErrorConstant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractBaseConnector implements IBaseConnector {

	private static final Logger logger = LoggerFactory.getLogger(AbstractBaseConnector.class);

	@Override
	public Map<String, String> postPaymentRequestForm(Map<String, String> paramMap) throws EmulationException {
		Map<String, String> paymentRequestMap = new HashMap<String, String>();
		return paymentRequestMap;
	}

	/*@Override
	public Map<String, String> postQueryRequest(Map<String, String> paramMap) throws EmulationException {
		Map<String, String> paymentRequestMap = new HashMap<String, String>();
		return paymentRequestMap;
	}

	@Override
	public Map<String, String> processReturn(Map<String, String> paramMap) throws EmulationException {
		Map<String, String> paymentRequestMap = new HashMap<String, String>();
		return paymentRequestMap;
	}

	@Override
	public Map<String, String> processCallBack(Map<String, String> paramMap) throws EmulationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> processCallBackJson(String jsonString) throws EmulationException {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	protected String jsonToString(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    protected Object stringToObject(String jsonInString, Class className)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInString, className);
	}

	protected void handleException(Exception exp) throws EmulationException {
		
	    logger.error("Error occured in AbstractBaseConnector :: ", exp);
	    if (exp instanceof EmulationException) {
            throw (EmulationException)exp;
        } else {
            throw new EmulationException(EmulationErrorConstant.INTERNAL_ERROR);
        }
	}
	
	protected IConnectorSignature getSignatureServiceBean(String className) throws EmulationException {

        IConnectorSignature service = null;
        try {
            Class<?> serviceClass = Class.forName(className);
            service = (IConnectorSignature) SpringApplicationContext.getApplicationContext().getBean(serviceClass);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new EmulationException(EmulationErrorConstant.INTERNAL_ERROR);
        }
        return service;
    }
}
