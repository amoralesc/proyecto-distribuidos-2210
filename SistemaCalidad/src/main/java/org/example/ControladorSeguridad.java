package org.example;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class ControladorSeguridad {

	public boolean validarPassword(String password, String salt, String hash) throws InvalidKeySpecException, NoSuchAlgorithmException {
		String hashValidar = this.generarHash(password, this.fromHex(salt));
		return hash.equals(hashValidar);
	}

	public String generarHash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		int iteraciones = 1000;
		char[] chars = password.toCharArray();

		PBEKeySpec spec = new PBEKeySpec(chars, salt, iteraciones, 64 * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = skf.generateSecret(spec).getEncoded();
		return toHex(hash);
	}

	public byte[] generarSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	public String toHex(byte[] arreglo) {
		BigInteger bi = new BigInteger(1, arreglo);
		String hex = bi.toString(16);
		int longitudRelleno = (arreglo.length * 2) - hex.length();
		if (longitudRelleno > 0) {
			return String.format("%0" + longitudRelleno + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	public byte[] fromHex(String hex) {
		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length ; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}
}
