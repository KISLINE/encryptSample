package com.kisline.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class encryptSample {
	private static final Logger LOGGER = LogManager.getLogger("encryptSample");

	public static void main(String[] args) {
		try {
			if (args.length != 1) {
				LOGGER.info("Usage : encryptSample [입력문자]");
				System.exit(1);
			}

			String inputValue = args[0];

			LOGGER.info("Plain Text : " + inputValue);
			LOGGER.info("SHA256 : " + getSHA256(inputValue));
			LOGGER.info("SHA256(URLEncoded) : " + URLEncoder.encode(getSHA256(inputValue), "UTF-8").toString());
			LOGGER.info("MD5 : " + getMD5(inputValue));
			LOGGER.info("MD5(URLEncoded) : " + URLEncoder.encode(getMD5(inputValue), "UTF-8").toString());
		} catch (Exception e) {
			LOGGER.error("main", e);
		}
	}

	public static String getSHA256(String s) throws IOException {
		try {
			return getCryptoSHA256String(s);
		} catch (Exception e) {
			LOGGER.error("getSHA256", e);
			throw new IOException(e.toString());
		}
	}

	public static String getMD5(String s) throws IOException {
		try {
			return getMD5String(s);
		} catch (Exception e) {
			LOGGER.error("getMD5", e);
			throw new IOException(e.toString());
		}
	}

	private static String getCryptoSHA256String(String inputValue) throws NoSuchAlgorithmException, IOException {
		if (inputValue == null) {
			return getCryptoSHA256String("default");
		}
		byte[] ret = digest("SHA-256", inputValue.getBytes());

		return encode(ret);
	}

	private static String getMD5String(String inputValue) throws NoSuchAlgorithmException, IOException {
		if (inputValue == null) {
			return getMD5String("default");
		}
		byte[] ret = digest("MD5", inputValue.getBytes());

		return encode(ret);
	}

	private static byte[] digest(String alg, byte[] input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(alg);
		return md.digest(input);
	}

	private static String encode(byte[] encodeBytes) throws IOException {
		byte[] buf = Base64.encodeBase64(encodeBytes);
		return new String(buf).trim();
	}

}
