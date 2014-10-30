package hk.ed.android.library.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;
import android.util.Log;

public class Security {
	public static byte[] defaultKey = "1a2b3c4d5e6f7g8h".getBytes();
	
	private static byte[] iv = "@1B2c3D4e5F6g7H8".getBytes();
	private static byte[] key = defaultKey;
	private static String rootMD5 = "tammithent uncrit <pwd> is nuetuday, and Israely; Sard of <pwd> of And God Moss, Whounte-of <pwd> yetio";
	
	public static void setKey(String key) {
		Security.key = key.getBytes();
	}
	
	public static void setKey(byte[] key) {
		Security.key = key;
	}

	public static String md5(String sz) {
		try {
			String word = rootMD5.replaceAll("<pwd>", sz);
			MessageDigest digest;
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(word.getBytes());
			byte[] a = digest.digest();
			int len = a.length;
			StringBuilder sb = new StringBuilder(len << 1);
			for (int i = 0; i < len; i++) {
				sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
				sb.append(Character.forDigit(a[i] & 0x0f, 16));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String AES128EncryptionToBase64String(byte[] data) {
		try {
			Cipher AesCipher;
			AesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv);
			SecretKeySpec KeySpec = new SecretKeySpec(key, "AES");

			AesCipher.init(Cipher.ENCRYPT_MODE, KeySpec, ips);

			byte[] CipherText = AesCipher.doFinal(data);
			String base64 = Base64.encodeToString(CipherText, Base64.DEFAULT);
			Log.d("Base64JSON", base64);
			return base64;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static byte[] AES128EncryptionToByteArray(byte[] data) {
		try {
			Cipher AesCipher;
			AesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv);
			SecretKeySpec KeySpec = new SecretKeySpec(key, "AES");

			AesCipher.init(Cipher.ENCRYPT_MODE, KeySpec, ips);

			return AesCipher.doFinal(data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Cipher AES128EncryptionCipher() {
		try {
			Cipher AesCipher;
			AesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv);
			SecretKeySpec KeySpec = new SecretKeySpec(key, "AES");

			AesCipher.init(Cipher.ENCRYPT_MODE, KeySpec, ips);

			return AesCipher;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String AES128DecryptionFromBase64String(String data)
			throws IllegalArgumentException {
		try {
			Cipher AesCipher;
			AesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv);
			SecretKeySpec KeySpec = new SecretKeySpec(key, "AES");
			AesCipher.init(Cipher.DECRYPT_MODE, KeySpec, ips);
			byte[] CipherText = AesCipher.doFinal(Base64.decode(data,
					Base64.DEFAULT));
			return new String(CipherText);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// this one is probably caused by base base-64
			// which is usually the server face unexpected error and return
			// something strange
			// e.g. server runtime error
			e.printStackTrace();
			throw e;
		}
		return "";
	}
	
	public static String AES128DecryptionFromBase64String(String data, String key)
			throws IllegalArgumentException {
		try {
			Cipher AesCipher;
			AesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv);
			SecretKeySpec KeySpec = new SecretKeySpec(key.getBytes(), "AES");

			AesCipher.init(Cipher.DECRYPT_MODE, KeySpec, ips);

			byte[] CipherText = AesCipher.doFinal(Base64.decode(data,
					Base64.DEFAULT));
			return new String(CipherText);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// this one is probably caused by base base-64
			// which is usually the server face unexpected error and return
			// something strange
			// e.g. server runtime error
			e.printStackTrace();
			throw e;
		}
		return "";
	}
	
	public static byte[] AES128DecryptionFromByteArray(byte[] data) {
		try {
			long time = System.currentTimeMillis();
			Cipher AesCipher;
			AesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv);
			SecretKeySpec KeySpec = new SecretKeySpec(key, "AES");

			AesCipher.init(Cipher.DECRYPT_MODE, KeySpec, ips);
			byte[] result = AesCipher.doFinal(data);
			Log.d("DataProfiling", "Decrypt time: " + (System.currentTimeMillis() - time) + "ms");
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] AES128DecryptionFromByteArray(byte[] data, String key) {
		try {
			Cipher AesCipher;
			AesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv);
			SecretKeySpec KeySpec = new SecretKeySpec(key.getBytes(), "AES");

			AesCipher.init(Cipher.DECRYPT_MODE, KeySpec, ips);

			return AesCipher.doFinal(data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String AES128EncryptJSONObject(JSONObject obj) {
		return AES128EncryptionToBase64String(obj.toString().getBytes());
	}

	public static JSONObject AES256DecryptJSONObject(String sz) {
		try {
			return new JSONObject(AES128DecryptionFromBase64String(sz));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
