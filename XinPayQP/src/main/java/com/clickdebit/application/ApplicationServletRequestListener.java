package com.clickdebit.application;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clickdebit.application.util.GenerateRandom;

@WebListener
public class ApplicationServletRequestListener implements ServletRequestListener {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationServletRequestListener.class);

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		logger.info("Request Destroyed");
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		MDC.put(ApplicationConstant.LOG4J_MDC_CONTEXT_TXN_PARAM, GenerateRandom.randomAlphaNumeric());
		logger.info("Request Initialized");
	}

}