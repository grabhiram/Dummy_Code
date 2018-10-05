package com.clickdebit.paysec.application;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexExtractor {
	
	private static final Logger logger = LoggerFactory.getLogger(RegexExtractor.class);
	
	public static String extract(String input, String regex, Integer groupNumber) {
		if(regex == null) {
			logger.info("Regex is null hence ignoring");
			return null;
		}
//		String [] tokens = regex.split(";");
		StringTokenizer st = new StringTokenizer(regex, "|");
		while(st.hasMoreTokens()) {
			String rg = st.nextToken();
			try {
				logger.info("input :: "+input);
				logger.info("regex :: "+rg);
				logger.info("groupNumber :: "+groupNumber);
				Pattern p = Pattern.compile(rg);
				Matcher m = p.matcher(input);

			    // if an occurrence if a pattern was found in a given string...
			    if (m.find()) {
					logger.info("m.group(groupNumber) :: "+m.group(groupNumber));
					String extractedText = m.group(groupNumber);
					if(extractedText != null && !extractedText.equals("null")) {
						logger.info("Extracted value :: "+extractedText);
				    	return extractedText;
					}
			    }
			} catch (Exception e) {
				logger.error("Unable to extract value :: "+e);
			}

		}
		
		return null;
	}

}
