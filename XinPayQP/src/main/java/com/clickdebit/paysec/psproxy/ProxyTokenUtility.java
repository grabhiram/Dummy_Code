package com.clickdebit.paysec.psproxy;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class ProxyTokenUtility {

	private static final Logger logger = LoggerFactory.getLogger(ProxyTokenUtility.class);

	public final static String ISSUER = "paysec.cnyproxy.com";
	private final static String version = "0.99123";

	public final static String EMULATION_AUTH_PARAM = "EMULATION-AUTH-PARAM-FOR-JWT";

	public static final String EMULATION_AUTH_HEADER_PREFIX = "Proxy:";
	
	public static final Integer EXPIRY_TIME_IN_MINUTES = 5;

	public static String getJWTEncryptedString(String keyStr, String toBeEncStr) {
		Date expires = getExpiryDate(EXPIRY_TIME_IN_MINUTES);
		logger.info("Date : " + expires.getTime());
		
		String[] roles = { "ADMIN", "USER" };
		if (toBeEncStr == null) {
			throw new NullPointerException("null Subject is illegal");
		}
//		if (roles == null) {
//			throw new NullPointerException("null roles are illegal");
//		}
//		if (expires == null) {
//			throw new NullPointerException("null expires is illegal");
//		}
		if (keyStr == null) {
			logger.warn("Key seems to be empty using default key.");
			keyStr = PlatformConstant.JWT_ENCRYPTION_KEY;
		}

//		toBeEncStr = EMULATION_AUTH_HEADER_PREFIX + toBeEncStr;
		
		SecureRandom random = new SecureRandom();
		byte[] sharedSecret = new byte[32];
		random.nextBytes(sharedSecret);
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

		// sharedSecret.
		String jwtString = Jwts.builder().setIssuer(ISSUER).setSubject(toBeEncStr)
				.setAudience(StringUtils.join(Arrays.asList(roles), ",")).setExpiration(expires).setIssuedAt(new Date())
				.setId(version).signWith(signatureAlgorithm, keyStr.getBytes()).compact();

		return EMULATION_AUTH_HEADER_PREFIX + jwtString;
	}

//	public static String getJWTEncryptedString(String keyStr, String toBeEncStr) {
//		if (toBeEncStr == null) {
//			throw new NullPointerException("null Subject is illegal");
//		}
//
//		if (keyStr == null) {
//			// throw new NullPointerException("null keyStr is illegal");
//			logger.warn("Key seems to be empty using default key.");
//			keyStr = PlatformConstant.JWT_ENCRYPTION_KEY;
//		}
//
//		SecureRandom random = new SecureRandom();
//		byte[] sharedSecret = new byte[32];
//		random.nextBytes(sharedSecret);
//		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
//
//		// sharedSecret.
//		String jwtString = Jwts.builder().setIssuer(ISSUER).setSubject(toBeEncStr).setExpiration(getExpiryDate(9876543))
//				.setIssuedAt(new Date()).setId(version).signWith(signatureAlgorithm, keyStr.getBytes()).compact();
//
//		return jwtString;
//	}

	private static Date getExpiryDate(int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}

	public static boolean isValid(String jwsToken, String keyString) {
		try {
			Jwts.parser().setSigningKey(keyString.getBytes()).parseClaimsJws(jwsToken.trim());
			return true;
		} catch (Exception e) {
			logger.error("Error Occured while verifying signiture", e);
			e.printStackTrace();
			return false;
		}
	}

	// "http://localHost:8080/test"

	public static void main(String... strings) throws Exception {

		String token = getJWTEncryptedString(PlatformConstant.JWT_ENCRYPTION_KEY, "http://localHost:8080/test");

		System.out.println("Auth token : " + token);
		
		System.out.println(isValid("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJwYXlzZWMuY255cHJveHkuY29tIiwic3ViIjoiaHR0cHM6Ly9wYXkueWl6aGlmdWJqLmNvbS9jdXN0b21lci9nYi9wYXlfYmFua191dGY4LmpzcCIsImF1ZCI6IkFETUlOLFVTRVIiLCJleHAiOjE1MTI1NDI1OTQsImlhdCI6MTUxMjU0MjI5NCwianRpIjoiMC45OTEyMyJ9.cRguO8veAu-FCIKTmFA4x_VxjEgOmIFH-SLilKgBVTyStef8OJNFrehWFy6asPphqhID9ln_IvbsbI885WjU5A",PlatformConstant.JWT_ENCRYPTION_KEY));
		
	}
}
