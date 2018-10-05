package com.clickdebit.paysec.connector.service.impl;

/*import java.util.HashMap;
*/import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.clickdebit.exception.EmulationException;
/*import com.clickdebit.paysec.application.WebApplicationUtils;
*/import com.clickdebit.paysec.connector.constant.ConnectorServiceConstant;
/*import com.clickdebit.paysec.connector.response.model.XinPayQPQueryResponse;
*/import com.clickdebit.paysec.connector.service.IBaseConnector;
import com.clickdebit.paysec.connector.service.IConnectorSignature;
/*import com.clickdebit.paysec.psproxy.ProxyTokenUtility;
import com.clickdebit.paysec.service.constant.PayInTransactionStatus;
import com.clickdebit.service.constants.EmulationErrorConstant;
*/
@Service
public class XinPayQPConnectorImpl extends AbstractBaseConnector implements IBaseConnector {

    private static final Logger logger = LoggerFactory.getLogger(XinPayQPConnectorImpl.class);
    private static String XINPAY_PARAM_VERSION = "version";
    private static String XINPAY_PARAM_CURRENCY = "currency";
    private static String XINPAY_PARAM_AMOUNT = "amount";
  /*  private static String XINPAY_PARAM_FIRSTNAME = "firstName";
    private static String XINPAY_PARAM_LASTNAME = "lastName";
    private static String XINPAY_PARAM_BILLING_EMAIL = "billingEmail";
    private static String XINPAY_PARAM_BILLING_STREET = "billingStreet";
    private static String XINPAY_PARAM_BILLING_CITY = "billingCity";
    private static String XINPAY_PARAM_BILLING_STATE = "billingState";
    private static String XINPAY_PARAM_BILLING_POST_CODE = "billingPostCode";
    private static String XINPAY_PARAM_BILLING_COUNTRY = "billingCountry";
    private static String XINPAY_PARAM_BILLING_PHONE = "billingPhone";*/
/*    private static String XINPAY_PARAM_PRODUCT_CODE = "productCode";
*/    private static String XINPAY_PARAM_CID = "cId";
    private static String XINPAY_PARAM_MERCHANT_ID = "merchantId";
    private static String XINPAY_PARAM_SIGNATURE = "signature";
    private static String XINPAY_PARAM_ORDER_REFERENCE = "orderRef";
    private static String XINPAY_PARAM_CALLBACK_URL = "callbackURL";
    private static String XINPAY_PARAM_RETURN_URL = "returnURL";
    private static String XINPAY_PARAM_BANK_CODE = "bankCode";
    private static String XINPAY_PARAM_WAP_ENABLED = "wapEnabled";
 /*   private static String XINPAY_PARAM_CART_ID = "cartId";
    private static String XINPAY_PARAM_SIGN_TYPE = "signType";
    private static String XINPAY_PARAM_STATUS = "status";*/

   /* private static String STATUS_SUCCESS = "00";
    private static String STATUS_FAILED = "99";*/

    @Override
    public Map<String, String> postPaymentRequestForm(Map<String, String> paramMap) throws EmulationException 
    {
        logger.info("Processing payment request for XINPAY");
        Map<String, String> paymentRequestMap = super.postPaymentRequestForm(paramMap);
        paymentRequestMap.put(XINPAY_PARAM_CURRENCY, paramMap.get(ConnectorServiceConstant.CURRENCY.getName()));
        paymentRequestMap.put(XINPAY_PARAM_AMOUNT, paramMap.get(ConnectorServiceConstant.AMOUNT.getName()));
      /*  paymentRequestMap.put(XINPAY_PARAM_FIRSTNAME, paramMap.get(ConnectorServiceConstant.FIRSTNAME.getName()));
        paymentRequestMap.put(XINPAY_PARAM_LASTNAME, paramMap.get(ConnectorServiceConstant.LASTNAME.getName()));
        paymentRequestMap.put(XINPAY_PARAM_BILLING_EMAIL,
                paramMap.get(ConnectorServiceConstant.BILLING_EMAIL.getName()));
        paymentRequestMap.put(XINPAY_PARAM_BILLING_STREET,
                paramMap.get(ConnectorServiceConstant.BILLING_STREET.getName()));
        paymentRequestMap.put(XINPAY_PARAM_BILLING_CITY,
                paramMap.get(ConnectorServiceConstant.BILLING_CITY.getName()));
        paymentRequestMap.put(XINPAY_PARAM_BILLING_STATE,
                paramMap.get(ConnectorServiceConstant.BILLING_STATE.getName()));
        paymentRequestMap.put(XINPAY_PARAM_BILLING_POST_CODE,
                paramMap.get(ConnectorServiceConstant.BILLING_POST_CODE.getName()));
        paymentRequestMap.put(XINPAY_PARAM_BILLING_COUNTRY,
                paramMap.get(ConnectorServiceConstant.BILLING_COUNTRY.getName()));
        paymentRequestMap.put(XINPAY_PARAM_BILLING_PHONE,
                paramMap.get(ConnectorServiceConstant.BILLING_PHONE.getName()));*/
        paymentRequestMap.put(XINPAY_PARAM_CID,
                paramMap.get(ConnectorServiceConstant.ORDER_REFERENCE_NUMBER.getName()));
        paymentRequestMap.put(XINPAY_PARAM_MERCHANT_ID,
                paramMap.get(ConnectorServiceConstant.MERCHANT_CODE.getName()));
        paymentRequestMap.put(XINPAY_PARAM_ORDER_REFERENCE,
                paramMap.get(ConnectorServiceConstant.ORDER_REFERENCE_NUMBER.getName()));
        paymentRequestMap.put(XINPAY_PARAM_CALLBACK_URL,
                paramMap.get(ConnectorServiceConstant.CALLBACK_URL.getName()));
        paymentRequestMap.put(XINPAY_PARAM_RETURN_URL, paramMap.get(ConnectorServiceConstant.RETURN_URL.getName()));
        paymentRequestMap.put(XINPAY_PARAM_BANK_CODE, paramMap.get(ConnectorServiceConstant.BANK_CODE.getName()));
        paymentRequestMap.put(XINPAY_PARAM_WAP_ENABLED, FALSE);

        IConnectorSignature signatureService = getSignatureServiceBean(
                paramMap.get(ConnectorServiceConstant.SIGNATURE_CLASS_NAME.getName()));
        paymentRequestMap.put(XINPAY_PARAM_SIGNATURE, signatureService.generatePaymentRequestSignature(paramMap));
        
        return paymentRequestMap;
    /* @Override
    public Map<String, String> postQueryRequest(Map<String, String> paramMap) throws EmulationException {

        logger.info("Processing query request for XINPAY");
        try {
            Map<String, String> queryRequestMap = super.postQueryRequest(paramMap);
            queryRequestMap.put(XINPAY_PARAM_CART_ID, paramMap.get(ConnectorServiceConstant.ORDER_REFERENCE_NUMBER.getName()));
            queryRequestMap.put(XINPAY_PARAM_AMOUNT, paramMap.get(ConnectorServiceConstant.AMOUNT.getName()));
            queryRequestMap.put(XINPAY_PARAM_MERCHANT_ID,
                    paramMap.get(ConnectorServiceConstant.MERCHANT_CODE.getName()));
            queryRequestMap.put(XINPAY_PARAM_SIGN_TYPE, "");
            
            IConnectorSignature signatureService = getSignatureServiceBean(
                    paramMap.get(ConnectorServiceConstant.SIGNATURE_CLASS_NAME.getName()));
            queryRequestMap.put(XINPAY_PARAM_SIGNATURE, signatureService.generateQueryRequestSignature(paramMap));
            
            Integer timeoutValue = Integer.parseInt(paramMap.get(ConnectorServiceConstant.TIMEOUT.getName()));

            String connectorQueryURL = paramMap.get(ConnectorServiceConstant.URL.getName());
            String authToken = null;
            if (connectorQueryURL != null && connectorQueryURL.length() > 0) {
                logger.info("connectorQueryURL :: " + connectorQueryURL);
                authToken = ProxyTokenUtility.getJWTEncryptedString(null, connectorQueryURL);
            }

            String responseStr = WebApplicationUtils.sendPostRequest(
                    paramMap.get(ConnectorServiceConstant.URL.getName()), queryRequestMap, authToken, timeoutValue);

            XinPayQPQueryResponse XinPayQPQueryResponse = (XinPayQPQueryResponse) stringToObject(responseStr,
                    XinPayQPQueryResponse.class);

            // Validate query response signature
            paramMap.put(ConnectorServiceConstant.STATUS.getName(),
                    PayInTransactionStatus.IN_PROGRESS.getValue() + "");
            if(XinPayQPQueryResponse.getStatus() != null) {
                if (XinPayQPQueryResponse.getStatus().equals(STATUS_SUCCESS)) {
                    paramMap.put(ConnectorServiceConstant.STATUS.getName(), PayInTransactionStatus.SUCCESS.getValue() + "");
                } else if (XinPayQPQueryResponse.getStatus().equals(STATUS_FAILED)) {
                    paramMap.put(ConnectorServiceConstant.STATUS.getName(), PayInTransactionStatus.FAILED.getValue() + "");
                }
            }
        } catch (Exception exp) {
            handleException(exp);
        }
        return paramMap;
    }

    @Override
    public Map<String, String> processReturn(Map<String, String> paramMap) throws EmulationException {
        logger.info("Processing connector return for XINPAY");

        return paramMap;
    }
    
    @Override
    public Map<String, String> processCallBack(Map<String, String> paramMap) throws EmulationException {
        logger.info("Processing connector callback for XINPAY");
        try {
            String responseSignVal = paramMap.get(XINPAY_PARAM_SIGNATURE);
            
            IConnectorSignature signatureService = getSignatureServiceBean(
                    paramMap.get(ConnectorServiceConstant.SIGNATURE_CLASS_NAME.getName()));
            
            Map<String, String> callbackRequestMap = new HashMap<>();
            callbackRequestMap.put(ConnectorServiceConstant.ORDER_REFERENCE_NUMBER.getName(), paramMap.get(XINPAY_PARAM_ORDER_REFERENCE));
            callbackRequestMap.put(ConnectorServiceConstant.AMOUNT.getName(), paramMap.get(XINPAY_PARAM_AMOUNT));
            callbackRequestMap.put(ConnectorServiceConstant.MERCHANT_CODE.getName(),
                    paramMap.get(ConnectorServiceConstant.MERCHANT_CODE.getName()));
            callbackRequestMap.put(ConnectorServiceConstant.MERCHANT_KEY.getName(), 
                    paramMap.get(ConnectorServiceConstant.MERCHANT_KEY.getName()));
            callbackRequestMap.put(ConnectorServiceConstant.STATUS.getName(), paramMap.get(XINPAY_PARAM_STATUS));
            
            String signVal = signatureService.generateCallbackRequestSignature(callbackRequestMap);
            
            if (signVal.equals(responseSignVal)) {
                
                String statusStr = paramMap.get(XINPAY_PARAM_STATUS);
                if (statusStr.equals(STATUS_SUCCESS)) {
                    paramMap.put(ConnectorServiceConstant.STATUS.getName(),
                            PayInTransactionStatus.SUCCESS.getValue() + "");
                } else if (statusStr.equals(STATUS_FAILED)) {
                    paramMap.put(ConnectorServiceConstant.STATUS.getName(),
                            PayInTransactionStatus.FAILED.getValue() + "");
                } else {
                    paramMap.put(ConnectorServiceConstant.STATUS.getName(),
                            PayInTransactionStatus.IN_PROGRESS.getValue() + "");
                }
                paramMap.put(ConnectorServiceConstant.CALLBACK_RESPONSE.getName(), SUCCESS);

            } else {
                throw new EmulationException(EmulationErrorConstant.INVALID_SIGNATURE);
            }

        } catch (Exception exp) {
            handleException(exp);
        }
        return paramMap;*/
    }

	public static String getXINPAY_PARAM_VERSION() {
		return XINPAY_PARAM_VERSION;
	}

	public static void setXINPAY_PARAM_VERSION(String xINPAY_PARAM_VERSION) {
		XINPAY_PARAM_VERSION = xINPAY_PARAM_VERSION;
	}

}
