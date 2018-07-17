package hr.fer.zemris.java.blog.web.servlets;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Utility class for generating SHA-1 hash from password for storing it into database.
 * @author Marin Jurjevic
 * 
 */
public class SHACrypto {

	/**
	 * Generates SHA-1 hash from given string (password in this case).
	 * @param password password whose hash will be generated
	 * @return SHA-1 hash
	 */
	public static String hashValue(String password) {
		String sha1 = null;
		MessageDigest crypt = null;
		try {
			crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(password.getBytes("UTF-8"));
			sha1 = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e1) {
		} catch (UnsupportedEncodingException e) {
		}
		return sha1;
	}

	/**
	 * Returns hexadecimal form of hash result.
	 * @param hash hash to be converted to hexadecimal.
	 * @return hexadecimal representation of hash
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}