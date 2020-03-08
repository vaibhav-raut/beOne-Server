package com.beone.shg.common.web.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Encryptor {

	protected final static String DEFAULT_SEPARATOR=";";
	protected SecretKey skey=null;
	protected Cipher cipher=null;
	protected String key = null;
	protected String separator=null;


	private static Logger  logger = LoggerFactory.getLogger(Encryptor.class);

	public Encryptor(String key) {
		this(key, DEFAULT_SEPARATOR);
	}


	public Encryptor(String key, String separator) {
		logger.debug("Encryptor key:" + key);
		this.key = key;
		skey = getSecretKey(key);
		this.separator = separator;
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");			
		} catch (Exception e) {
			logger.error("Cannot create Cipher instance.", e);
		}
	}

	public String encrypt(ShgPassToken source) {
		if(cipher == null) {
			return null;
		}
		String str = tokenToString(source);
		logger.debug("Encrypting the string: " + str);
		byte[] cbytes = null;
		String ret = null;
		try {

			cipher.init(Cipher.ENCRYPT_MODE, skey);
			cbytes = cipher.doFinal(str.getBytes());

			//ret =  Base64.encodeBase64String(cbytes);
			Base64 base64 = new Base64(true);			
			ret = base64.encodeAsString(cbytes);
			ret = ret.replaceAll("\\s+", "");
		} catch (Exception e) {
			logger.error("Cannot encrypt the token.", e);
		} 
		return ret;
	}

	public ShgPassToken decrypt(String str) {
		logger.debug("Decrypting the string: "+str);
		if(cipher == null) {
			return null;
		}
		ShgPassToken token = null;
		//byte[]bytes = Base64.decodeBase64(str);
		Base64 base64 = new Base64(true);
		byte[]bytes = base64.decode(str);

		try {
			cipher.init(Cipher.DECRYPT_MODE, skey);
			byte[] rbytes = cipher.doFinal(bytes);
			token = stringToToken(new String(rbytes));
		} catch (Exception e) {
			logger.error("Cannot decrypt the token.", e);
		}
		return token;
	}


	protected  SecretKey getSecretKey(String key) {
		byte[] salt = "choose a better salt".getBytes();
		int iterations = 1000;
		SecretKeyFactory factory;
		SecretKey skey=null;
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			SecretKey tmp = factory.generateSecret(new PBEKeySpec(key.toCharArray(), salt, iterations, 128));
			skey = new SecretKeySpec(tmp.getEncoded(), "AES");

		} catch (Exception e) {
			logger.error("Cannot generate secret key.",e);
		}

		return skey;
	}

	
	protected String tokenToString(ShgPassToken token) {
		StringBuilder sb = new StringBuilder();

		sb.append(token.getTimestamp());
		sb.append(separator);
		sb.append(token.getKey());
		sb.append(separator);
		sb.append(Math.random());
		sb.append(separator);
		sb.append(token.getMemberAcNo());
		sb.append(separator);
		sb.append(token.getSelectedGroupAcNo());
		sb.append(separator);
		sb.append(Math.random());
		sb.append(separator);
		sb.append(token.getWsAccess());
		sb.append(separator);
		sb.append(token.getUiAccess());

		logger.debug("tokenToString:"+sb);
		return sb.toString();
	}

	protected ShgPassToken stringToToken(String str) {

		ShgPassToken token = null;
		if(str != null) {
			String []items = str.split(separator);
			
			if(items.length == 8) {
				try {
					token = new ShgPassToken(Long.parseLong(items[0]),
							items[1],
							Long.parseLong(items[3]),
							Long.parseLong(items[4]),
							Long.parseLong(items[6]),
							Long.parseLong(items[7]));

				} catch(NumberFormatException nfe) {
					nfe.printStackTrace();
				}
			}
		}
		return token;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}
}




