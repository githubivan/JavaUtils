package com.util.jdbc.tool;

import java.util.Locale;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HexUtil {
	private static final Logger logger = LoggerFactory.getLogger(HexUtil.class);
	
	public static byte[] hexStrToByteArray(String s) {
		if(s == null || s.isEmpty()) {
			return null;
		}
		try {
			int len = s.length();
			if (len % 2 == 0) {
				byte[] data = new byte[len / 2];
				for (int i = 0; i < len; i += 2) {
					data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
							.digit(s.charAt(i + 1), 16));
				}
				return data;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("hexStrToByteArray error", e);
		}
		return null;
	}
	
	public static String byteArrayToHexStr(byte[] data) {
		if (null == data) {
			return null;
		}
		return Hex.encodeHexString(data).toUpperCase(Locale.ENGLISH);
	}

	public static byte[] strToSha256(String str) {
		if (str != null) {
			return DigestUtils.sha256(str);
		}
		return null;
	}
	
	public static int byteArrayToInt(byte[] b) {
	    int value = 0;
	    for (int i = 0; i < 4; i++) {
	        int shift = (4 - 1 - i) * 8;
	        value += (b[i] & 0x000000FF) << shift;
	    }
	    return value;
	}
	
	public static byte[] intToByteArray(int a) {
	    byte[] ret = new byte[4];
	    ret[3] = (byte) (a & 0xFF);   
	    ret[2] = (byte) ((a >> 8) & 0xFF);   
	    ret[1] = (byte) ((a >> 16) & 0xFF);   
	    ret[0] = (byte) ((a >> 24) & 0xFF);
	    return ret;
	}
}
