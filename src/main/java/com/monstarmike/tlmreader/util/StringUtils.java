package com.monstarmike.tlmreader.util;

import java.util.regex.Pattern;

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
	

	/**
	 * convert a byte array inverted into BCD character string representation
	 * @param bytes array of bytes
	 * @param start start position  (start + size - 1) 
	 * @param size iteration length
	 * @return string with converted characters
	 */
	public static String bcdEncodeInverted(byte[] bytes, int start, int size) {
		StringBuffer sb = new StringBuffer();
		for (int i = start+size-1; i >= start; --i) {
			sb.append(String.format("%02X", bytes[i])); //$NON-NLS-1$
		}
		if (Pattern.compile(".*[ABCDEF]").matcher(sb.toString()).find()) {
			System.err.println("invalid input " + sb);
			return "0";
		}
		return sb.toString();
	}

}
