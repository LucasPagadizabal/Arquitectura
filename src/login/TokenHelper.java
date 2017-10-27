package login;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class TokenHelper {
	
	private final static String SECRET_KEY = "myKey";
	private final static Map<String, String> TOKENS = new ConcurrentHashMap<>();

    public static String generarToken(String userName) {
        long minutes = System.currentTimeMillis() / 1000 / 60;
        String key = UUID.randomUUID().toString().toUpperCase() + "|" + userName + "|" + minutes;
        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        jasypt.setPassword(SECRET_KEY);
        return jasypt.encrypt(key);
    }

    public static boolean tokenValido(String token) {
        try {
            StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
            jasypt.setPassword(SECRET_KEY);
            jasypt.decrypt(token);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

	public static void setToken(String token, String username) {
		TOKENS.put(token, username);
	}

	public static boolean isValidoToken(String token) {
		return TOKENS.containsKey(token);
	}

}

