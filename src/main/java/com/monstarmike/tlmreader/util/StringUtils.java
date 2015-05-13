package com.monstarmike.tlmreader.util;

public class StringUtils {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 3];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 3] = hexArray[v >>> 4];
			hexChars[j * 3 + 1] = hexArray[v & 0x0F];
			hexChars[j * 3 + 2] = ' ';
		}
		return new String(hexChars);
	}

	public static String bytesToDec(byte[] bytes) {
		String[] decParts = new String[bytes.length * 2];
		for (int i = 0; i < bytes.length; i++) {
			decParts[i * 2] = new Integer(bytes[i] & 0xff).toString();
			decParts[i * 2 + 1] = " ";
		}
		return org.apache.commons.lang.StringUtils.join(decParts);
	}
}
