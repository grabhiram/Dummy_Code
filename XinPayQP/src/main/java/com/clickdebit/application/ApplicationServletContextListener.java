package com.clickdebit.application;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@WebListener
public class ApplicationServletContextListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationServletContextListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("------------ ServletContextListener destroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Initializing Context listner during serverstartup");
		try{	
//			ApplicationDaemonThread t = new ApplicationDaemonThread();
//			t.setSleepInterval(ApplicationConstant.TRANSACTION_CLEANUP_SLEEP_INTERVAL_MILLIS);
//			t.setDaemon(false);
//			logger.debug("Invoking ApplicationDaemonThread for cleaning up transaction...");
//	
//			t.start();
//			logger.info("ApplicationDaemonThread successfully invoked");
		} catch (Exception exp) {
			logger.error("Error occcured while invoking Daemon thread.", exp);
		}
		
		
	}
}
