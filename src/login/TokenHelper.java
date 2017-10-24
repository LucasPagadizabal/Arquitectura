package login;

import java.util.UUID;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class TokenHelper {
	public static String generarToken(String userName){
		long minutes = System.currentTimeMillis() / 1000 / 60;
		String key = UUID.randomUUID().toString().toUpperCase() +
				"|" + userName +
				"|" + minutes;
	StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
		jasypt.setPassword(userName);
		String authenticationToken = jasypt.encrypt(key);
		return authenticationToken;
	}

	public static void setToken(String token, String username) {
		
		
	}

}

