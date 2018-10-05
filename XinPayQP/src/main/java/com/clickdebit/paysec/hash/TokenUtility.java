package com.clickdebit.paysec.hash;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clickdebit.controller.constant.ApplicationConstant;
import com.clickdebit.exception.EmulationException;
import com.clickdebit.properties.PropertyLoader;
import com.clickdebit.service.constants.EmulationErrorConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenUtility {

	private static final Logger logger = LoggerFactory.getLogger(TokenUtility.class);

	public final static String ISSUER = "payment.paysec.com";
	private final static String VERSION = "0.1";
//	private static String JWT_ENCRYPTION_KEY = "Wq1@3$qweRkl8ghqopbhjdC782311";
	private static String JWT_ENCRYPTION_KEY = "L(*&OUHdasfHKJH*&TYasdvoicuaiokUGUYY*$#@#@KJHK";

	private static String EXPIRY_PERIOD = "9876543";
	//private static String EXPIRY_PERIOD = "30";

	static {
		loadTokenProperties();
	}

	public static String getJWTEncryptedString(String toBeEncStr) {
		if (toBeEncStr == null) {
			throw new NullPointerException("null Subject is illegal");
		}
		SecureRandom random = new SecureRandom();
		byte[] sharedSecret = new byte[32];
		random.nextBytes(sharedSecret);
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

		// sharedSecret.
		String jwtString = Jwts.builder().setIssuer(ISSUER).setSubject(toBeEncStr)
				.setExpiration(getExpiryDate(Integer.parseInt(EXPIRY_PERIOD))).setIssuedAt(new Date()).setId(VERSION)
				.signWith(signatureAlgorithm, JWT_ENCRYPTION_KEY.getBytes()).compact();

		return jwtString;
	}

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

	public static TokenParams getTokenParams(String jwsToken) throws EmulationException {
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(JWT_ENCRYPTION_KEY.getBytes())
					.parseClaimsJws(jwsToken.trim());
			TokenParams tokenParams = new TokenParams();
			Claims tokenBody = claimsJws.getBody();
			String[] subjectArr = getParsedSubjectParams(tokenBody.getSubject());
			tokenParams.setTransactionId(subjectArr[0]);
			tokenParams.setTransactionUUID(subjectArr[1]);
			tokenParams.setVersion(tokenBody.getId());
			tokenParams.setIssuer(tokenBody.getIssuer());
			tokenParams.setIssuedAt(tokenBody.getIssuedAt());
			tokenParams.setExpirationDate(tokenBody.getExpiration());
			return tokenParams;
		} catch (Exception e) {
			logger.error("Error Occured while verifying signiture", e);
			throw new EmulationException(EmulationErrorConstant.INVALID_TOKEN);
		}
	}

	private static String[] getParsedSubjectParams(String subject) throws Exception {
		String[] subjectArr = subject.split(":");
		if (null == subjectArr || subjectArr.length != 2) {
			throw new Exception("Invalid Subject parameter in token.");
		}
		return subjectArr;
	}

	private boolean checkVersion(String version) throws Exception {
		if (null != version && !version.isEmpty()) {
			return true;
		}
		throw new Exception("Version parameter not found...");
	}

	private boolean checkGransactionIds(String transactionId, String transactionUUID) throws Exception {
		if (null != transactionId && !transactionId.isEmpty() && null != transactionUUID
				&& !transactionUUID.isEmpty()) {
			return true;
		}
		throw new Exception("Transaction id parameter not found...");
	}

	private Boolean validateExpirationDate(Date expiryDate) throws Exception {
		if (null != expiryDate) {
			long curTime = System.currentTimeMillis();
			if (curTime < expiryDate.getTime()) {
				return true;
			}
			throw new Exception("Token expired...");
		}
		throw new Exception("Token expired...");
	}

	public void validateToken(TokenParams tokenParams) throws Exception {
		if (null != tokenParams) {
			checkIssuer(tokenParams.getIssuer());
			validateExpirationDate(tokenParams.getExpirationDate());
			checkGransactionIds(tokenParams.getTransactionId(), tokenParams.getTransactionUUID());
			checkVersion(tokenParams.getVersion());
		} else {
			throw new Exception("Invalid token...");
		}
	}

	private boolean checkIssuer(String issuer) throws Exception {
		if (null != issuer && !issuer.isEmpty() && issuer.equals(ISSUER)) {
			return true;
		}
		throw new Exception("Invalid issuer...");
	}

	public static boolean isValid(String token, Key key) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(token.trim());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String getName(String jwsToken, Key key) {
		if (isValid(jwsToken, key)) {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
			return claimsJws.getBody().getSubject();
		}
		return null;
	}

	public static String[] getRoles(String jwsToken, Key key) {
		if (isValid(jwsToken, key)) {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
			return claimsJws.getBody().getAudience().split(",");
		}
		return new String[] {};
	}

	public static String getVersion(String jwsToken, Key key) {
		if (isValid(jwsToken, key)) {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
			return claimsJws.getBody().getId();
			// return Integer.parseInt(claimsJws.getBody().getId());
		}
		return "-1";
	}

	public static String getAccountId(String jwsToken, Key key) {
		if (isValid(jwsToken, key)) {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
			return claimsJws.getBody().getSubject();
		}
		return null;
	}

	private static void loadTokenProperties() {
//		JWT_ENCRYPTION_KEY = PropertyLoader.readProperty(ApplicationConstant.TRANSACTION_TOKEN_AUTH_KEY_PROP_NAME,
//				JWT_ENCRYPTION_KEY);
//		EXPIRY_PERIOD = PropertyLoader.readProperty(ApplicationConstant.TRANSACTION_TOKEN_EXIPRY_PROP_NAME,
//				EXPIRY_PERIOD);
	}

	public static void main(String... strings) throws Exception {
		Date expiryDate = getExpiryDate(9876543);
		String toBeEncStr = "MerchantName:MerchantKey";
		String token = getJWTEncryptedString(toBeEncStr);
		System.out.println(token);

		TokenParams params = getTokenParams(token);

		System.out.println("params: " + params);
		System.out.println("is valid : " + isValid(token, JWT_ENCRYPTION_KEY));
		
		System.out.println(isValid("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJwYXltZW50LnBheXNlYy5jb20iLCJzdWIiOiIxMjM1NDpudWxsIiwiZXhwIjoyMTA1MDgxODgxLCJpYXQiOjE1MTI0ODkzMDEsImp0aSI6IjAuMSJ9.49ZWZS8ePCVKm1APDDWjPUlfqz8idKKFU-TCcza3Oixuxm19S4elNHyYhf681FoSD4ZDxbsrKFYxL2JLpgWUFA",JWT_ENCRYPTION_KEY));
		System.out.println(isValid("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJwYXlzZWMuY255cHJveHkuY29tIiwic3ViIjoiaHR0cHM6Ly9wYXkueWl6aGlmdWJqLmNvbS9jdXN0b21lci9nYi9wYXlfYmFua191dGY4LmpzcCIsImF1ZCI6IkFETUlOLFVTRVIiLCJleHAiOjE1MTI0OTA0MDMsImlhdCI6MTUxMjQ5MDEwMywianRpIjoiMC45OTEyMyJ9.TCGg-85K2p36WtEgBwPnqDc4fLsSO_84pYIkNx4HWZ-TnWZtaB65X7INaOlOqvbpAQ1MjIQnCdpLOQZ6o2_yng",JWT_ENCRYPTION_KEY));
		// AuthenticationFilter f = new AuthenticationFilter();
		// f.validateToken(t);
	}
}
