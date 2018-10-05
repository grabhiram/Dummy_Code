package com.clickdebit.application.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clickdebit.exception.EmulationException;
import com.clickdebit.service.constants.EmulationErrorConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApplicationUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationUtils.class);
	
	public static String dateFormat = "dd-MM-yyyy hh:mm";
    
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
    
	public static String getCurrentTime() {
		long milliSeconds = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
	}
	
	public static String downloadFile(String fileURL, String saveFile) throws EmulationException {
		if(fileURL != null && fileURL.toLowerCase().startsWith("https")) {
			return downloadFileHTTPS(fileURL, saveFile);
		} else {
			return downloadFileHTTP(fileURL, saveFile);
		}
	}

	
	private static String downloadFileHTTPS(String fileURL, String saveFile) throws EmulationException {
		try {
			URL e = new URL(fileURL);
			HttpsURLConnection conn = (HttpsURLConnection) e.openConnection();
			int responseCode = conn.getResponseCode();
			String fileName = "";
			if (responseCode != HttpURLConnection.HTTP_OK) {
				logger.info("No file to download. Server replied HTTP code: " + responseCode);
				logger.error("Unable to download file from : " + fileURL);
				throw new EmulationException(EmulationErrorConstant.INTERNAL_ERROR);
			} else {
				String disposition = conn.getHeaderField("Content-Disposition");
				String contentType = conn.getContentType();
				int contentLength = conn.getContentLength();
				if (disposition != null) {
					int inputStream = disposition.indexOf("filename=");
					if (inputStream > 0) {
						fileName = disposition.substring(inputStream + 10, disposition.length() - 1);
					}
				} else {
					fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
				}

				logger.info("Content-Type = " + contentType);
				logger.info("Content-Disposition = " + disposition);
				logger.info("Content-Length = " + contentLength);
				logger.info("fileName = " + fileName);
				InputStream inputStream1 = conn.getInputStream();
				String saveFilePath = saveFile + fileName;
				logger.info("saveFilePath = " + saveFilePath);
				FileOutputStream outputStream = new FileOutputStream(saveFilePath);
				// boolean bytesRead = true;
				byte[] buffer = new byte[4096];

				int bytesRead1;
				while ((bytesRead1 = inputStream1.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead1);
				}

				outputStream.close();
				inputStream1.close();
				logger.info("File downloaded");
				conn.disconnect();
				return fileName;
			}
		} catch (IOException arg14) {
			logger.error("Unable to download file from : " + fileURL);
			throw new EmulationException(EmulationErrorConstant.INTERNAL_ERROR, arg14);
		}
	}

	private static String downloadFileHTTP(String fileURL, String saveFile) throws EmulationException {
		try {
			URL e = new URL(fileURL);
			HttpURLConnection conn = (HttpURLConnection) e.openConnection();
			int responseCode = conn.getResponseCode();
			String fileName = "";
			if (responseCode != HttpURLConnection.HTTP_OK) {
				logger.info("No file to download. Server replied HTTP code: " + responseCode);
				logger.error("Unable to download file from : " + fileURL);
				throw new EmulationException(EmulationErrorConstant.INTERNAL_ERROR);
			} else {
				String disposition = conn.getHeaderField("Content-Disposition");
				String contentType = conn.getContentType();
				int contentLength = conn.getContentLength();
				if (disposition != null) {
					int inputStream = disposition.indexOf("filename=");
					if (inputStream > 0) {
						fileName = disposition.substring(inputStream + 10, disposition.length() - 1);
					}
				} else {
					fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
				}

				logger.info("Content-Type = " + contentType);
				logger.info("Content-Disposition = " + disposition);
				logger.info("Content-Length = " + contentLength);
				logger.info("fileName = " + fileName);
				InputStream inputStream1 = conn.getInputStream();
				String saveFilePath = saveFile + fileName;
				logger.info("saveFilePath = " + saveFilePath);
				FileOutputStream outputStream = new FileOutputStream(saveFilePath);
				// boolean bytesRead = true;
				byte[] buffer = new byte[4096];

				int bytesRead1;
				while ((bytesRead1 = inputStream1.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead1);
				}

				outputStream.close();
				inputStream1.close();
				logger.info("File downloaded");
				conn.disconnect();
				return fileName;
			}
		} catch (IOException arg14) {
			logger.error("Unable to download file from : " + fileURL);
			throw new EmulationException(EmulationErrorConstant.INTERNAL_ERROR, arg14);
		}
	}

	public static String callPOSTWebservice(String urlS, Object obj, String authKey) {
		String jsonInString = getJsonFromJava(obj);
		if(urlS != null && urlS.toLowerCase().startsWith("https")) {
			return callHTTPSWebService(urlS, jsonInString, authKey);
		} else {
			return callHTTPWebService(urlS, jsonInString);
		}
	}

	public static String callPOSTWebserviceJSON(String urlS, String jsonInString, String authKey, String symmetricKey) {
		String encryptedJsonString = jsonInString;
		try {
			if(symmetricKey != null && symmetricKey.length() > 0 ) {
				encryptedJsonString = TextEncrytDecryptor.encrypt(symmetricKey, jsonInString);
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Unable to encrypt json string :: "+jsonInString+" with symmetric key :: "+symmetricKey, e);
		}
		if(urlS != null && urlS.toLowerCase().startsWith("https")) {
			return callHTTPSWebService(urlS, encryptedJsonString, authKey);
		} else {
			return callHTTPWebService(urlS, encryptedJsonString);
		}
	}

	private static String getJsonFromJava(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("Unable to parse object to prepare JSON string", e);
//			throw new EmulationException(EmulationError.INTERNAL_ERROR, e);
		}
		return jsonInString;
	}
	
	private static String callHTTPWebService(String urlS, String jsonInString) {
		try {

			URL url = new URL(urlS);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

			OutputStream os = conn.getOutputStream();
//			os.write(jsonInString.getBytes());
			os.write(jsonInString.getBytes("UTF-8"));
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			StringBuffer sb = new StringBuffer();
			logger.debug("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				logger.debug(output);
				sb.append(output);
			}

			conn.disconnect();

			return sb.toString();

		} catch (MalformedURLException e) {
			logger.error("Unable to process webservice request to emulation", e);
//			throw new EmulationException(EmulationError.INTERNAL_ERROR, e);
		} catch (IOException e) {
			logger.error("Unable to process webservice request to emulation", e);
//			throw new EmulationException(EmulationError.INTERNAL_ERROR, e);
		}
		
		return null;
	}

	private static String callHTTPSWebService(String urlS, String jsonInString, String authKey) {
		try {
			
			TrustManager[] trustAllCerts = new TrustManager[] {
		       new X509TrustManager() {
		          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		            return null;
		          }

		          public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

		          public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

		       }
		    };

		    SSLContext sc = null;
			try {
				sc = SSLContext.getInstance("SSL");
			    sc.init(null, trustAllCerts, new java.security.SecureRandom());
			} catch (NoSuchAlgorithmException e) {
				logger.error("Unable to process webservice request to emulation", e);
			} catch (KeyManagementException e) {
				logger.error("Unable to process webservice request to emulation", e);
			}
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		    // Create all-trusting host name verifier
		    HostnameVerifier allHostsValid = new HostnameVerifier() {
		        public boolean verify(String hostname, SSLSession session) {
		          return true;
		        }
		    };
		    
		    // Install the all-trusting host verifier
		    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			
//		    logger.info(urlS);
//		    logger.info(jsonInString);

			URL url = new URL(urlS);
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setRequestProperty("Authorization", authKey);

			OutputStream os = conn.getOutputStream();
//			os.write(jsonInString.getBytes());
			os.write(jsonInString.getBytes("UTF-8"));
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			StringBuffer sb = new StringBuffer();
			logger.debug("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				logger.debug(output);
				sb.append(output);
			}

			conn.disconnect();

			return sb.toString();

		} catch (MalformedURLException e) {
			logger.error("Unable to process webservice request to emulation", e);
//			throw new EmulationException(EmulationError.INTERNAL_ERROR, e);
		} catch (IOException e) {
			logger.error("Unable to process webservice request to emulation", e);
//			throw new EmulationException(EmulationError.INTERNAL_ERROR, e);
		}
		
		return null;
	}


	
}
