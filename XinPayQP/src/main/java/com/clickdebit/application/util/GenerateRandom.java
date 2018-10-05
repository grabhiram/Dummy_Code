package com.clickdebit.application.util;

public class GenerateRandom {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final Integer STRING_LENGTH = 8;
	
	public static String randomAlphaNumeric() {
		return randomAlphaNumeric(STRING_LENGTH);
	}
	
	public static String randomAlphaNumeric(int length) {
		int count = length;
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	public static void main(String[] args) {
		for(int i = 0; i<10; i++)
			System.out.println(randomAlphaNumeric());
		System.out.println(randomAlphaNumeric(128));
		
	}
}
