package com.lgu.ccss.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class SecureManager {

    /**
     * UserKey Byte Array
     */
    private static final byte[] USER_KEY = {
        78, 80, 101, 102, 77, 115, 51, 117, 70,
        49, 65, 107, 125, 43, 118, 67, 74, 120,
        90, 87, 48, 65, 38, 57
    };

    /**
     * SecretKeySpec 인스턴스
     */
    private static SecretKeySpec KEYSPEC = new SecretKeySpec(USER_KEY, "DESede");


    /**
     * 파라미터 인코딩 메서드
     *
     * @param plainStr 인코딩할 파라미터 String
     * @return 인코딩 결과값
     * @throws Exception 인코딩 Exception
     */
    public static String encodeParam( String plainStr ) throws Exception {
        String ret = null;
        try {
        	Base64 bs = new Base64();
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(1, KEYSPEC);
            byte plainText[] = plainStr.getBytes("UTF-8");
            byte cipherText[] = cipher.doFinal(plainText);
            ret = new String(bs.encode(cipherText));
        }
        catch (Exception e) {
            throw e;
        }
        return ret;
    }

    public static String encodeParam( String plainStr, String encodingChar ) throws Exception {
        String ret = null;
        try {
        	Base64 bs = new Base64();
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(1, KEYSPEC);
            byte plainText[] = plainStr.getBytes(encodingChar);
            byte cipherText[] = cipher.doFinal(plainText);
            ret = new String(bs.encode(cipherText));
        }
        catch (Exception e) {
            throw e;
        }
        return ret;
    }

    /**
     * 파라미터 디코딩 메서드
     *
     * @param encodedStr 디코딩할 파라미터 String
     * @return 디코딩 결과값
     * @throws Exception 디코딩 Exception
     */
    public static String decodeParam( String encodedStr ) throws Exception {
        String ret = null;
        try {
        	Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(2, KEYSPEC);          
            byte base64bytes[] =  Base64.decodeBase64(encodedStr.getBytes());
            byte decryptedText[] = cipher.doFinal(base64bytes);
            ret = new String(decryptedText, "UTF-8");
            ret = ret.trim();
        }
        catch (Exception e) {
            throw e;
        }
        return ret;
    }

    public static String decodeParam( String encodedStr, String encodingChar ) throws Exception {
        String ret = null;
        try {
        	Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(2, KEYSPEC);
            byte base64bytes[] =  Base64.decodeBase64(encodedStr.getBytes());
            byte decryptedText[] = cipher.doFinal(base64bytes);
            ret = new String(decryptedText, encodingChar);
            ret = ret.trim();
        }
        catch (Exception e) {
            throw e;
        }
        return ret;
    }
    
    public static String encryptSHA256(String encodedStr){
		String rtnSHA = "";
		
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(encodedStr.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			
			for(int i = 0 ; i < byteData.length ; i++){
			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			rtnSHA = sb.toString();
			    
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			rtnSHA = null; 
		}
		return rtnSHA;
    }

    
	public static String getSecureKey(int length) {
		Random random = new Random();

		StringBuffer str = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (random.nextBoolean()) {
				str.append(String.valueOf(
						(char) ((int) (random.nextInt(26)) + 97)).toUpperCase());
			} else {
				str.append((random.nextInt(10)));
			}
		}
		return str.toString();
	}

    public static void main(String[] args) throws Exception {
		String testStr = "01089890001";
		String enStr = encodeParam(testStr);
		System.out.println("01089890001:" + enStr);
		
		String deStr = decodeParam(enStr);
		System.out.println(deStr);
		
		String d = "e1HnML1GVoiwp36k7ffoCw==";
		System.out.println(decodeParam(d));
		
		String sha256Str = "lgtestcam01";
		String ensha256 = encryptSHA256(sha256Str);
		System.out.println("lgtestcam01: "  + ensha256);
		
		String decodeMdn = decodeParam("LSDHqz65M0ZWA0faV1J86w==");
		System.out.println("$$$$$$$$$$" +decodeMdn);
    }
}


