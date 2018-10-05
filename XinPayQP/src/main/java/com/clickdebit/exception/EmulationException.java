package com.clickdebit.exception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clickdebit.service.constants.EmulationErrorConstant;

public class EmulationException extends Exception {
	
	private static final Logger logger = LoggerFactory.getLogger(EmulationException.class);
	
	private static final long serialVersionUID = 1L;
	
	private EmulationErrorConstant error;

	public EmulationException(EmulationErrorConstant error, Exception ex) {
		super(error.getMessage(), ex);
		logger.error(error.getMessage(), this);
		this.error = error;
	}
	
	public EmulationException(EmulationErrorConstant error) {
		super(error.getMessage());
		logger.error(error.getMessage(), this);
		this.error = error;
	}

	public EmulationException(EmulationErrorConstant error, List<String> values) {
		super(error.getDynamicMessage(values));
		logger.error(error.getDynamicMessage(values), this);
		this.error = error;
	}

	public EmulationErrorConstant getError() {
		return error;
	}

}
