package com.clickdebit.paysec.application;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetURLExtractor {

	private static final Logger logger = LoggerFactory.getLogger(GetURLExtractor.class);
	
	public static void main(String... strings) throws UnsupportedEncodingException {
		String url = "http://www.fn184.com/pay?id=1021278&token=ce3b3f89e5a3de45fa4a03d29fb18a08";
//		String url = "http://www.fn184.com/pay";
		logger.info(url);
		Map<String, String> dataMap = new HashMap<>();
		String baseURL = extractData(url, dataMap);
		logger.info(baseURL);
		logger.info(dataMap.toString());
	}

	public static String extractData(String url, Map<String, String> dataMap) {

		int i = url.indexOf("?");
		String baseURL = url;
		if (i > -1) {
			String searchURL = url.substring(url.indexOf("?") + 1);
			logger.info("Search URL: " + searchURL);
			populateMap(searchURL, dataMap);
			baseURL = url.substring(0, i);
		}
		logger.info("Base URL "+baseURL);

		return baseURL;
	}

	public static void populateMap(String search, Map<String, String> dataMap) {
		String params[] = search.split("&");

		for (String param : params) {
			String temp[] = param.split("=");
			try {
				logger.info(temp[0]+" :: "+temp[1]);
				dataMap.put(temp[0], java.net.URLDecoder.decode(temp[1], "UTF-8"));
				logger.info(temp[0]+" :: "+java.net.URLDecoder.decode(temp[1], "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error("Unable to extract data from URL Decoder",e);
			}
		}
	}
	
}
