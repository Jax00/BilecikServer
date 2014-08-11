package pl.jax.engine.log.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;

public class InnCrypto {
	
	public static final String ALPHANUM_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	public static final String ALPHANUM_SPECIAL_ALPHABET = ALPHANUM_ALPHABET + "-=!@#$%^&*()_+[]{};':|,./<>?";
	
	public static String hexEncode(byte[] aInput) {
		StringBuilder result = new StringBuilder();
		char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		for (int i=0; i<aInput.length; i++) {
			byte b = aInput[i];
			result.append(digits[(b & 0xf0) >> 4]);
			result.append(digits[b & 0x0f]);
		}
		return result.toString();
	}
	
	public static byte[] hexDecode(String aInput) throws Exception {
		if ((aInput.length() % 2) != 0)
			throw new IllegalArgumentException();
		byte[] data = new byte[aInput.length() /2];
		for (int i=0; i<aInput.length(); i+=2) {
			data[i/2] = (byte) ((Character.digit(aInput.charAt(i), 16) << 4) + Character.digit(aInput.charAt(i+1), 16));
		}
		return data;
	}
	
	public static String clearHexInput(String aInput) {
		return aInput.toLowerCase().replaceAll("[^a-f0-9]", "");
	}
	
	private static char encodeAsChar(int number, String alphabet) {
		return alphabet.charAt(Math.abs(number) % alphabet.length());
	}
	
	public static String createSessionId(String userName) {
		try {
			SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
			String msg = userName + Calendar.getInstance().getTimeInMillis() + prng.nextLong();
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			return hexEncode(sha.digest(msg.getBytes()));
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new String();
		}
	}
	
	public static String generateSalt(int length) {
		return generateSalt(length, ALPHANUM_SPECIAL_ALPHABET);
	}
	
	public static String generateSalt(int length, String alphabet) {
		try {
			SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
			String salt = new String();
			for (int i=0; i<length; i++) {
				salt += encodeAsChar(prng.nextInt(), alphabet);
			}
			return salt;
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new String();
		}
	}
	
	public static String hashPassword(String password, String salt) {
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-512");
			String msg = password + salt;
			return hexEncode(sha.digest(msg.getBytes()));
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
