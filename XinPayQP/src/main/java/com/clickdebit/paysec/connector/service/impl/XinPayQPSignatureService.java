package com.clickdebit.paysec.connector.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.clickdebit.exception.EmulationException;
import com.clickdebit.paysec.connector.constant.ConnectorServiceConstant;
import com.clickdebit.paysec.connector.service.IConnectorSignature;
import com.clickdebit.paysec.hash.GenerateBCryptSignature;

@Service
public class XinPayQPSignatureService implements IConnectorSignature {

	private static final Logger logger = LoggerFactory.getLogger(XinPayQPSignatureService.class);
	
	@Override
	public String generatePaymentRequestSignature(Map<String, String> paramMap) throws EmulationException {
		StringBuilder sb = new StringBuilder();
		sb.append(paramMap.get(ConnectorServiceConstant.MERCHANT_CODE.getName())).append(DELIMITER_SEMI_COLON);
		sb.append(paramMap.get(ConnectorServiceConstant.ORDER_REFERENCE_NUMBER.getName())).append(DELIMITER_SEMI_COLON);
		sb.append(paramMap.get(ConnectorServiceConstant.AMOUNT.getName())).append(DELIMITER_SEMI_COLON);
		sb.append(paramMap.get(ConnectorServiceConstant.CURRENCY.getName()));
		return GenerateBCryptSignature.generateHash(sb.toString(), paramMap.get(ConnectorServiceConstant.MERCHANT_KEY.getName()));
	}


	
	private String generateSignature(Map<String, String> paramMap) throws EmulationException {
		StringBuilder sb = new StringBuilder();
		sb.append(paramMap.get(ConnectorServiceConstant.MERCHANT_CODE.getName())).append(DELIMITER_SEMI_COLON);
		sb.append(paramMap.get(ConnectorServiceConstant.ORDER_REFERENCE_NUMBER.getName())).append(DELIMITER_SEMI_COLON);
		sb.append(paramMap.get(ConnectorServiceConstant.AMOUNT.getName())).append(DELIMITER_SEMI_COLON);
		sb.append(paramMap.get(ConnectorServiceConstant.STATUS.getName()));
		return GenerateBCryptSignature.generateHash(sb.toString(), paramMap.get(ConnectorServiceConstant.MERCHANT_KEY.getName()));
	}

}
