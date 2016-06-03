package com.nzion.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtilHash {

	public final static String generateHash(String algorithm, String text) throws NoSuchAlgorithmException {
	MessageDigest md = MessageDigest.getInstance(algorithm);
	md.reset();
	md.update(text.getBytes());
	byte messageDigest[] = md.digest();
	StringBuffer hexString = new StringBuffer();
	for (int i = 0; i < messageDigest.length; i++) {
		hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
	}
	return hexString.toString();
	}

}
