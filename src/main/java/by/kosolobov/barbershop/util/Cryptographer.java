package by.kosolobov.barbershop.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class Cryptographer {
    private static final Logger log = LogManager.getLogger(Cryptographer.class);
    private static final int ITERATIONS = 20_000;
    private static final int SALT_LEN = 32;
    private static final int DESIRED_KEY_LEN = 255;

    private static String hash(String password, byte[] salt) throws IllegalArgumentException {
        if (password == null || password.length() == 0) {
            log.log(Level.ERROR, "Illegal password for encryption: {}", password);
            throw new IllegalArgumentException("Empty password");
        }
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(
                    password.toCharArray(), salt, ITERATIONS, DESIRED_KEY_LEN
            ));
            return Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.log(Level.FATAL, "ERROR: SecretKeyFactory does not support this algorithm: {}", e.getMessage(), e);
            throw new RuntimeException("Illegal SecretKeyFactory algorithm", e);
        }
    }

    public static boolean check(String password, String stored) {
        String[] saltAndPassword = stored.split("\\$");
        if (saltAndPassword.length != 2) {
            throw new IllegalStateException("Stored password does not match 'salt$hash'");
        }
        String hash = hash(password, Base64.getDecoder().decode(saltAndPassword[0]));
        return hash.equals(saltAndPassword[1]);
    }

    public static String getSaltedHash(String password) {
        try {
            byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(SALT_LEN);
            return Base64.getEncoder().encodeToString(salt) + "$" + hash(password, salt);
        } catch (NoSuchAlgorithmException e) {
            log.log(Level.ERROR, "ERROR: SecureRandom does not support this algorithm: {}", e.getMessage(), e);
            throw new RuntimeException("Illegal SecureRandom algorithm", e);
        }
    }
}
