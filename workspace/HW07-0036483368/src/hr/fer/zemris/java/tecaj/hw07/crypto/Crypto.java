package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.OperationNotSupportedException;

/**
 * Crypto is a simple program that allows user to encrypt/decrypt given file
 * using the AES crypto-algorithm and the 128-bit encryption key. It also can
 * check the SHA-256 file digest.
 * 
 * @author Marin Jurjevic
 *
 */
public class Crypto {

	/**
	 * Starting point of this program. It is
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 */
	public static void main(String[] args) throws OperationNotSupportedException, NoSuchAlgorithmException,
			FileNotFoundException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException {

		if (args.length < 2 || args.length > 3) {
			throw new IllegalArgumentException("Invalid number of arguments");
		}

		switch (args[0]) {
		case "checksha":
			checkSHA(args[1]);
			break;
		case "decrypt":
			execute(args[1], args[2], false);
			break;
		case "encrypt":
			execute(args[1], args[2], true);
			break;
		default:
			throw new OperationNotSupportedException("No such operation!");
		}

	}

	/**
	 * Checks if given SHA is valid digest of given file through command line.
	 * 
	 * @param file
	 *            name of the file we check SHA
	 * @throws NoSuchAlgorithmException
	 *             if algorithm is not present
	 * @throws FileNotFoundException
	 *             if file is not present
	 */
	private static void checkSHA(String file) throws NoSuchAlgorithmException, FileNotFoundException {
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		
		System.out.println("Please provide expected sha-256 digest for " + file + ": ");
		Scanner sc = new Scanner(System.in);
		String expectedDigest = sc.nextLine();
		sc.close();

		byte[] hash = null;
		try (BufferedInputStream bf = new BufferedInputStream(new FileInputStream(file))) {

			byte[] buffer = new byte[1024];

			while (true) {
				int r = bf.read(buffer);
				if (r < 1) {
					hash = sha.digest();
					break;
				}
				sha.update(buffer, 0, r);
			}

		} catch (IOException ex) {
			System.out.println("Error occured while trying to read file!");
		}

		System.out.print("Digesting completed. Digest of " + file);
		if (bytesToHex(hash).equals(expectedDigest)) {
			System.out.print(" matches expected digest");
		} else {
			System.out.printf(" does not match expected digest. Digest was: %n" + bytesToHex(hash));
		}

	}

	/**
	 * Converts String to an array of bytes.
	 * 
	 * @param text
	 *            String to be converted
	 * @return array of bytes representing this String
	 */
	private static byte[] hexToByte(String text) {
		int len = text.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(text.charAt(i), 16) << 4) + Character.digit(text.charAt(i + 1), 16));
		}

		return data;
	}

	/**
	 * Converts array of bytes to a String
	 * 
	 * @param a
	 *            array of bytes
	 * @return String representation of this array
	 */
	private static String bytesToHex(byte[] a) {
		StringBuilder sb = new StringBuilder(a.length * 2);
		for (byte b : a)
			sb.append(String.format("%02x", b & 0xff));
		return sb.toString();
	}

	/**
	 * Executes encryption/decryption based on given operation in command line.
	 * 
	 * @param origFile
	 *            original file to be converted, encrypted or decrypted based on
	 *            encrypt argument
	 * @param cryFile
	 *            encrypted/decrypted file after performing given operation on
	 *            origFile
	 * @param encrypt
	 *            flag for determining operation, true is if encryption is ON,
	 *            false for decryption
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	private static void execute(String origFile, String cryFile, boolean encrypt) throws InvalidKeyException,
			InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {

		// reading input from cmd
		Scanner sc = new Scanner(System.in);
		System.out.println("Please provide password as hex-encoded text:");
		String keyText = sc.nextLine();

		System.out.println("Please provide initialization vector as hex-encoded text:");
		String ivText = sc.nextLine();
		sc.close();

		// Cipher initilization
		SecretKeySpec keySpec = new SecretKeySpec(hexToByte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hexToByte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

		// Encrypting/Decrypting
		try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(origFile));
				BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(cryFile))) {
			byte[] buffer = new byte[4096];
			byte[] cipherText;

			while (true) {
				int r = is.read(buffer);

				if (r < 1) {
					cipherText = cipher.doFinal();

					break;
				}
				cipherText = cipher.update(buffer, 0, r);
				os.write(cipherText);
			}

			os.write(cipherText);

		} catch (IOException ex) {
			System.out.println("Error occured while trying to read file!");
			System.exit(-1);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		// End of program message
		String operation = encrypt ? "Encryption" : "Decryption";
		System.out.println(operation + " completed. Generated " + cryFile + " based on " + origFile);
	}
}
