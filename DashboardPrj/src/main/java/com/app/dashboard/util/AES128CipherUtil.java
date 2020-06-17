package com.app.dashboard.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 암호화 유틸
 * @author 김동건 (dgkim@bsgglobal.com)
 */
public class AES128CipherUtil {
	
	private static final String secretKey = "1234567890abcdef"; // 16bit
	private static String IV = secretKey; // 16bit
	
	// 암호화
	public static String AES_Encode(String str) {
		String encodedString = null;
		try {
			byte[] keyData = secretKey.getBytes();
			SecretKey secureKey = new SecretKeySpec(keyData, "AES");

			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));

			byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
			encodedString = new String(Base64.encodeBase64(encrypted));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encodedString;
	}

	// 복호화
	public static String AES_Decode(String str) {
		String decodedString = null;
		try {
			byte[] keyData = secretKey.getBytes();
			SecretKey secureKey = new SecretKeySpec(keyData, "AES");

			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes("UTF-8")));

			byte[] byteStr = Base64.decodeBase64(str.getBytes());
			decodedString = new String(c.doFinal(byteStr), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decodedString;
	}
	
	/**
	 * 테스트
	 * @param args
	 */
	public static void main(String[] args) {
		String password = "1234aa!";
		String encodedId = AES128CipherUtil.AES_Encode(password);
		System.out.println("encoded : " + encodedId);
		String decodedId = AES128CipherUtil.AES_Decode(encodedId);
		System.out.println("decoded : " + decodedId);
	}
}
