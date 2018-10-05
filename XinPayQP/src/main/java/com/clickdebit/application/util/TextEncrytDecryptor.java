package com.clickdebit.application.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextEncrytDecryptor {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationUtils.class);
	
	public static final String AES = "AES";
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    private static byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }
    
    public static String encrypt(String encryptionKey, String text) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
       
    	logger.debug("Encrypting user plain text:");
    	byte[] bytekey = hexStringToByteArray(encryptionKey);
    	SecretKeySpec sks = new SecretKeySpec(bytekey, TextEncrytDecryptor.AES);
    	Cipher cipher = Cipher.getInstance(TextEncrytDecryptor.AES);
    	cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
    	byte[] encrypted = cipher.doFinal(text.getBytes());
    	String encryptedText = byteArrayToHexString(encrypted);
    	
    	logger.debug("User text ecryprtion was successful.");
    	
    	return encryptedText;
   }
   
    public static String decrypt(String encryptionKey, String text) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    	logger.debug("Decrypting user encrypted text:");
    	byte[] bytekey = hexStringToByteArray(encryptionKey);
		SecretKeySpec sks = new SecretKeySpec(bytekey, TextEncrytDecryptor.AES);
		Cipher cipher = Cipher.getInstance(TextEncrytDecryptor.AES);
		cipher.init(Cipher.DECRYPT_MODE, sks);
		byte[] decrypted = cipher.doFinal(hexStringToByteArray(text));
		String decryptedText = new String(decrypted);

		logger.debug("User text decryption was successful.");
		
		return decryptedText;
   }
   
    public static String generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(TextEncrytDecryptor.AES);
        keyGen.init(128);
        SecretKey sk = keyGen.generateKey();
        String key = byteArrayToHexString(sk.getEncoded());
        System.out.println("key:" + key);
        return key;
    }
    
    public static void main(String[] args) {
    	try {
    		String key = "95BF21FA20CBAB37C4B3BEC827E935A9";//TextEncrytDecryptor.generateKey();
    		String encryptedText = "73F3A49FA4F8FC967D315EDEB355066A";;//TextEncrytDecryptor.encrypt(key, "Guahan1974!");
    		String decryptText = TextEncrytDecryptor.decrypt(key, encryptedText);
    		System.out.println("Key :: "+key+", encryptedText :: "+encryptedText+", decryptedText :: "+decryptText);
    	} catch(Exception e) {
    		
    	}
    }
    // username : 334414BA1CC2BCDF7F32077BABF1A23B6C288A6081A58618259ED53D602799AC
    //Password : 73F3A49FA4F8FC967D315EDEB355066A
    
}
