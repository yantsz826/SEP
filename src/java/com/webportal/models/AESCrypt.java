/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webportal.models;
/*from http://doraprojects.net/blog/?p=1276*/
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESCrypt
{
	private static final String ALGORITHM = "AES";
	private static final String KEY = "3Htfh367aHfDeJ79"; /*16 char*/

	public static String encrypt(String value) throws Exception
	{
		Key key = generateKey();
		Cipher cipher = Cipher.getInstance(AESCrypt.ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
		String encryptedValue64 = Base64.getEncoder().encodeToString(encryptedByteValue);
		return encryptedValue64;

	}

	public static String decrypt(String value) throws Exception
	{
		Key key = generateKey();
		Cipher cipher = Cipher.getInstance(AESCrypt.ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decryptedValue64 = Base64.getDecoder().decode(value);
		byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
		String decryptedValue = new String(decryptedByteValue, "utf-8");
		return decryptedValue;

	}

	private static Key generateKey() throws Exception
	{
		Key key = new SecretKeySpec(AESCrypt.KEY.getBytes(), AESCrypt.ALGORITHM);
		return key;
	}
}