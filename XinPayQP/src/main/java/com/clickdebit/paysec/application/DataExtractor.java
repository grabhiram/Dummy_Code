package com.clickdebit.paysec.application;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataExtractor {
	
	private static final Logger logger = LoggerFactory.getLogger(DataExtractor.class);
	
	private static int PRETTY_PRINT_INDENT_FACTOR = 4;
	
	private static String convertJsonString(String xmlString) {
		if (null == xmlString) {
			return null;
		}
		JSONObject xmlJsonObj = XML.toJSONObject(xmlString);
		return xmlJsonObj.toString(PRETTY_PRINT_INDENT_FACTOR);
	}
	public static String getXMLNodeValue(String xmlString, String xmlPath, String nodeDelim) throws JsonProcessingException, IOException {
		if(xmlPath == null ||xmlPath.length() <= 0) {
			logger.info("No path found, hence returning null from parser");
			return null;
		}
		String jsonString = convertJsonString(xmlString);
		logger.info("Converted json :: "+jsonString);
		String nodeValue = getJSONNodeValue(jsonString, xmlPath, nodeDelim);
		logger.info("Extracted '"+nodeValue+"' for path :: "+xmlPath);
		return nodeValue;
	}

	public static String getJSONNodeValue(String jsonString, String jsonPath, String nodeDelim) throws JsonProcessingException, IOException {
		if(jsonPath == null ||jsonPath.length() <= 0) {
			logger.info("No path found, hence returning null from parser");
			return null;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(jsonString);
		String[] jsonPathArr = jsonPath.split(nodeDelim);
		JsonNode node = root;
		for (int len = 0; len < jsonPathArr.length; len++) {
			String nodeName = jsonPathArr[len];
			int nodeIndex = 0;
			if (nodeName.contains("[") && nodeName.contains("]")) {
				Pattern regex = Pattern.compile("(.*?)\\[(.*?)\\]");
				Matcher matcher = regex.matcher(nodeName);
				while(matcher.find()) {
					nodeName = matcher.group(1);
		    			nodeIndex = Integer.parseInt(matcher.group(2));
		        }
			}
			node = node.get(nodeName);
			if (null != node && node.isArray()) {
				node = node.get(nodeIndex);
			}
		}
		String text = null;
		if(node != null) {
			text = node.asText();
		}
		logger.info("Extracted '"+text+"' for path :: "+jsonPath);
		return null == node ? null : node.asText();
	}
	
	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public static void main (String ... args) {
		try {
			
			String content = readFile("/Users/mihir/Downloads/json.json", StandardCharsets.UTF_8);
			String nodeVal = getJSONNodeValue(content, "retMsg", "\\.");
			// "contact[1].ref"
			//id
			System.out.println("nodeVal = " + nodeVal);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
}
