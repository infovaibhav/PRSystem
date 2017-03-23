package org.iry.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.iry.exceptions.IryException;

/**
 * The following example implements a class for encrypting and decrypting strings using several Cipher algorithms. The
 * class is created with a key and can be used repeatedly to encrypt and decrypt strings using that key. Some of the
 * more popular algorithms are: Blowfish DES DESede PBEWithMD5AndDES PBEWithMD5AndTripleDES TripleDES
 * 
 * @author vpatil
 */
public class StringEncrypter {

	/**
	 * PASSPHRASE Pass Phrase used to initialize both the encrypter and decrypter instances.
	 */
	private static final String PASSPHRASE = "!@#khdfhvgh76(18_";

	/** ecipher. */
	private Cipher ecipher;

	/** dcipher. */
	private Cipher dcipher;

	/**
	 * Constructor used to create this object. Responsible for setting and initializing this object's encrypter and
	 * decrypter Chipher instances given a Pass Phrase and algorithm.
	 * 
	 * @throws IryException - if initialization fails.
	 */
	public StringEncrypter() throws IryException {

		// 8-bytes Salt
		final byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x34,
				(byte) 0xE3, (byte) 0x03 };

		// Iteration count
		final int iterationCount = 19;

		try {

			final KeySpec keySpec = new PBEKeySpec(PASSPHRASE.toCharArray(), salt, iterationCount);
			final SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			// Prepare the parameters to the cipthers
			final AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

		} catch (InvalidAlgorithmParameterException e) {
			throw new IryException("EXCEPTION: InvalidAlgorithmParameterException : " + e.getLocalizedMessage(),
				e);
		} catch (InvalidKeySpecException e) {
			throw new IryException("EXCEPTION: InvalidKeySpecException : " + e.getLocalizedMessage(), e);
		} catch (NoSuchPaddingException e) {
			throw new IryException("EXCEPTION: NoSuchPaddingException : " + e.getLocalizedMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			throw new IryException("EXCEPTION: NoSuchAlgorithmException : " + e.getLocalizedMessage(), e);
		} catch (InvalidKeyException e) {
			throw new IryException("EXCEPTION: InvalidKeyException : " + e.getLocalizedMessage(), e);
		}
	}

	/**
	 * Takes a single String as an argument and returns an Encrypted version of that String.
	 * 
	 * @param str String to be encrypted
	 * @return <code>String</code> Encrypted version of the provided String
	 * @throws IryException - if encryption fails.
	 */
	public String encrypt(final String str) throws IryException {
		try {
			// Encode the string into bytes using utf-8
			final byte[] utf8 = str.getBytes("UTF8");

			// Encrypt
			final byte[] enc = ecipher.doFinal(utf8);

			// Encode bytes to base64 to get a string
			return new String(Base64.encodeBase64(enc));

		} catch (BadPaddingException e) {
			throw new IryException("EXCEPTION: BadPaddingException : " + e.getLocalizedMessage(), e);
		} catch (IllegalBlockSizeException e) {
			throw new IryException("EXCEPTION: IllegalBlockSizeException : " + e.getLocalizedMessage(), e);
		} catch (UnsupportedEncodingException e) {
			throw new IryException("EXCEPTION: UnsupportedEncodingException : " + e.getLocalizedMessage(), e);
		}
	}

	/**
	 * Takes a encrypted String as an argument, decrypts and returns the decrypted String.
	 * 
	 * @param str Encrypted String to be decrypted
	 * @return <code>String</code> Decrypted version of the provided String
	 * @throws IryException - if encryption fails.
	 */
	public String decrypt(final String str) throws IryException {
		try {
			// Decode base64 to get bytes
			final byte[] dec = Base64.decodeBase64(str.getBytes());

			// Decrypt
			final byte[] utf8 = dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (BadPaddingException e) {
			throw new IryException("EXCEPTION: BadPaddingException : " + e.getLocalizedMessage(), e);
		} catch (IllegalBlockSizeException e) {
			throw new IryException("EXCEPTION: IllegalBlockSizeException : " + e.getLocalizedMessage(), e);
		} catch (UnsupportedEncodingException e) {
			throw new IryException("EXCEPTION: UnsupportedEncodingException : " + e.getLocalizedMessage(), e);
		}
	}

	public static void main(String[] args) {
		try {
			StringEncrypter encrypter = new StringEncrypter();
			String test = encrypter.encrypt("");
			System.out.println(test);
			System.out.println(encrypter.decrypt(test));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}