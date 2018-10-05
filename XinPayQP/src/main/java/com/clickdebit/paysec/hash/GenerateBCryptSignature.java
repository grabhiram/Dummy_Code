package com.clickdebit.paysec.hash;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clickdebit.exception.EmulationException;
import com.clickdebit.service.constants.EmulationErrorConstant;

public class GenerateBCryptSignature {

	private static final Logger logger = LoggerFactory.getLogger(GenerateBCryptSignature.class);

	private final static String TOKEN_DELIMITER = ";";

	public static String generateHash(String plainText, String key) throws EmulationException {
		plainText = hashSHAWith256(plainText);
		String hashValue = BCrypt.hashpw(plainText, key);
		hashValue = hashValue.replace(key,"");
		return (hashValue);
	}
	
	public static boolean verifyHash(String plainText, String key, String generatedHash) {
		String hashValue = key+generatedHash;
		boolean isHashverified = BCrypt.checkpw(plainText, hashValue);
		return isHashverified;
	}

	public static String generateRequestTokenHash(List<String> paramList, String salt) throws EmulationException {
		String concatString = concatString(paramList);
		logger.info(concatString);
//		String hashVal = generateHash(hashSHAWith384(concatString), salt);
		String hashVal = generateHash(concatString, salt);

		logger.info("Generated hash is : " + hashVal);
		return hashVal;
//		String actualHashVal = hashVal.substring(salt.length(), hashVal.length());
//		logger.info("Generated hash value after trim : " + actualHashVal);
//		return actualHashVal;
	}

	private static String concatString(List<String> paramList) {
		StringBuilder sb = new StringBuilder();
		int cnt = 1;
		for (String value : paramList) {
			sb.append(value);
			if (cnt < paramList.size()) {
				sb.append(TOKEN_DELIMITER);
			}
			cnt++;
		}
		return sb.toString();
	}

	private static String hashSHAWith256(String textToHash) throws EmulationException {
		try {
//			String sha384Hex = DigestUtils.sha384Hex(textToHash);
			String sha256Hex = DigestUtils.sha256Hex(textToHash);
			logger.info("sha256Hex : " + sha256Hex);
			return sha256Hex;
		} catch (Exception e) {
			logger.error("Unable to get sha hash for text :: "+textToHash, e);
			throw new EmulationException(EmulationErrorConstant.SIGNATURE_PARSE_ISSUE);
		}
	}
	
}
