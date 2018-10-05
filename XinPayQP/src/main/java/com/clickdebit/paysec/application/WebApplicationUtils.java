package com.clickdebit.paysec.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clickdebit.paysec.service.model.FormResubmission;

public class WebApplicationUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(WebApplicationUtils.class);
	
	public static String dateFormat = "dd-MM-yyyy hh:mm";
  
	public static String callPOSTWebservice(String urlS, String jsonInString, String contentType, String authToken, int timeOut) {
		
		logger.info("Request URL :: "+urlS);
		logger.info("Request string :: "+jsonInString);
		logger.info("Content type :: "+contentType);
		logger.info("authToken :: "+authToken);
		String encryptedJsonString = jsonInString;
		if(urlS != null && urlS.toLowerCase().startsWith("https")) {
			return callHTTPSWebService(urlS, encryptedJsonString, contentType, authToken, timeOut);
		} else {
			return callHTTPWebService(urlS, encryptedJsonString, contentType, authToken, timeOut);
		}
	}
	
	private static String callHTTPWebService(String urlS, String jsonInString, String contentType, String authToken, int timeOut) {
		try {
			
			URL url = new URL(urlS);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(timeOut * 1000);
			conn.setReadTimeout(timeOut * 1000);
//			conn.setRequestProperty("Content-Type", "application/json");
//			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setRequestProperty("Content-Type", contentType);
			if(authToken != null) {
				conn.setRequestProperty("Authorization", authToken);
			}

			OutputStream os = conn.getOutputStream();
//			os.write(jsonInString.getBytes());
			os.write(jsonInString.getBytes("UTF-8"));
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK
				&& conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
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

	private static String callHTTPSWebService(String urlS, String jsonInString, String contentType, String authToken, int timeOut) {
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
			conn.setConnectTimeout(timeOut * 1000);
			conn.setReadTimeout(timeOut * 1000);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Content-Type", contentType);
			if(authToken != null) {
				conn.setRequestProperty("Authorization", authToken);
			}

			OutputStream os = conn.getOutputStream();
//			os.write(jsonInString.getBytes());
			os.write(jsonInString.getBytes("UTF-8"));
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK
				&& conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
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
	
	
	public static String sendGet(String url, Map<String,String> getDataMap, String authToken,int timeOut) throws IOException{
		
		url = populateWithGetDataMap(url, getDataMap);
		url=url.replace(" ", "%20");
		URL obj = null;
		HttpURLConnection con = null;
		BufferedReader in = null;
		StringBuffer response = new StringBuffer();
		int responseCode = 0;
		Charset charset = Charset.forName("UTF-8");
		obj = new URL(url);
		con = (HttpURLConnection) obj.openConnection();
		con.setConnectTimeout(timeOut * 1000);
		con.setReadTimeout(timeOut * 1000);
		con.setRequestMethod("GET");
		if(authToken != null) {
			logger.info("authToken :: "+authToken);
			con.setRequestProperty("Authorization", authToken);
		}
		
		logger.info("Sending 'GET' request to URL : " + url);
		responseCode = con.getResponseCode();
		logger.info("Response Code : " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) {
			in = new BufferedReader(  new InputStreamReader(con.getInputStream(),charset));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		}else{
			logger.info("Error Response from target :: "+responseCode);
		}
		if (null != in) {
			in.close();
		}
		
		logger.info("Response from server :: "+response.toString());
		return response.toString();
	}


	
	private static String populateWithGetDataMap(String url, Map<String, String> getDataMap) {
		if(url != null) {
			StringBuffer sbURL = new StringBuffer();
			sbURL.append(url);
			
			if(getDataMap != null && getDataMap.size() > 0) {
				int i = 0;
				for(String s:getDataMap.keySet()) {
					if(i == 0) {
						sbURL.append("?");
					} else {
						sbURL.append("&");
					}
					sbURL.append(s);
					sbURL.append("=");
					sbURL.append(getDataMap.get(s));
					i++;
				}
			}
			return sbURL.toString();
		}
			
		return null;
	}

	public static String sendPostRequest(String url, Map<String, String> dataMap, String authToken, int timeOut) throws ClientProtocolException, IOException {
		return sendPostRequest(url, dataMap, authToken, timeOut, new HashMap<String, String>() );
	}

	public static String sendPostRequest(String url, Map<String, String> dataMap, String authToken, int timeOut,Map<String, String> headerMap) throws ClientProtocolException, IOException {
		return sendPostRequestWithTimeout(url, dataMap, authToken, timeOut,headerMap );
	}
	
	public static String sendPostRequestWithTimeout(String url, Map<String, String> dataMap, String authToken, int timeout ) throws ClientProtocolException, IOException {
		return sendPostRequestWithHeader(url, dataMap, authToken, timeout, new HashMap<String, String>());
	}
	
	public static String sendPostRequestWithTimeout(String url, Map<String, String> dataMap, String authToken, int timeout , Map<String, String> headerMap) throws ClientProtocolException, IOException {
		return sendPostRequestWithHeader(url, dataMap, authToken, timeout, headerMap);
	}
	
	
	public static String sendPostRequestWithHeader(String url, Map<String, String> dataMap, String authToken, int timeout, Map<String, String> headerMap) throws ClientProtocolException, IOException {

		if(null == url) {
			logger.info("No URL found :: "+url);
			return null;
		}
		logger.info("Posting request on url " + url);

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(timeout * 1000)
				.setSocketTimeout(timeout * 1000).build();
		
		HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		HttpPost post = new HttpPost(url);
		
		// add +
		post.setHeader("User-Agent", "Mozilla/5.0");
//		post.setHeader( "Content-Type", "application/x-www-form-urlencoded;charset=utf-8;");
		post.setHeader( "Content-Type", "application/x-www-form-urlencoded");
		post.setHeader( "charset", "UTF-8");
		if(authToken != null) {
			logger.info("authToken :: "+authToken);
			post.setHeader("Authorization", authToken);
		}
		if(headerMap != null) {
			for(Map.Entry<String, String> header: headerMap.entrySet()) {
				post.setHeader(header.getKey(),header.getValue());
				logger.info("header :: "+header.getKey());
			}
		}
		
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		
		for(String key : dataMap.keySet()) {
			String paramValue = dataMap.get(key);
			urlParameters.add(new BasicNameValuePair(key, paramValue));
		//	logger.info("Param key :: "+key+", param value :: "+paramValue);
		}
		
		logger.info("Posting request on " + url + " with data set : ");
		urlParameters.forEach(param -> logger.info(param.getName() + "->" + param.getValue()));
		
		post.setEntity(new UrlEncodedFormEntity(urlParameters, Consts.UTF_8));
		
//		for(Header h:post.getAllHeaders()) {
//			logger.info("Request headers :: "+h.getValue());
//		}

		HttpResponse response = client.execute(post);
		logger.info("\nSending 'POST' request to URL : " + url);
		logger.info("Post parameters : " + post.getEntity());
		logger.info("Response Code : " +
                                    response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		logger.info("Response from URL::"+result.toString());
		
//		if(resubmitResponse) {
//			return handleResubmission(result.toString());
//		}
		
		return result.toString();

	}
	
	private static FormResubmission getDataForResubmission(String html) {
		FormResubmission fr = new FormResubmission();
//		Map<String, Map<String,String>> map = new HashMap<>();
		Document doc = Jsoup.parse(html);
		Elements eList = doc.getElementsByTag("form");
		
		String url = null;
		String method = null;
		if(eList != null) {
			for(Element e:eList) {
				Attributes aList = e.attributes();
				if(aList != null) {
					for(Attribute a:aList) {
						if(a.getKey().equalsIgnoreCase("action")) {
							logger.info("URL :: "+a.getValue());
							url = a.getValue();
						}
						if(a.getKey().equalsIgnoreCase("method")) {
							logger.info("Method :: "+a.getValue());
							method = a.getValue();
						}
					}
				}
				Map<String, String> formMap = new HashMap<>();
				List<Node> nList = e.childNodes();
				if(nList != null) {
					for(Node n:nList) {
						Attributes aL = n.attributes();
						if(aList != null) {
							String key = null;
							String value = null;
							for(Attribute a:aL) {
								if(a.getKey().equalsIgnoreCase("name") || a.getKey().equalsIgnoreCase("id")) {
									key = a.getValue();
								}
								if(a.getKey().equalsIgnoreCase("value")) {
									value = a.getValue();
								}
							}
							if(key != null) {
								logger.info("key :: '"+key+"', value :: '"+value+"'");
								formMap.put(key, value);
							}
						}
					}
				}
				fr.setUrl(url);
				fr.setParams(formMap);
				fr.setMethod(method);
//				map.put(url, formMap);
			}
		}
		
		return fr;
	}
	
}
