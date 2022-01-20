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
import java.util.Optional;

public class Cryptographer {
    private static final Logger log = LogManager.getLogger(Cryptographer.class);
    private static final int ITERATIONS = 20_000;
    private static final int SALT_LEN = 32;
    private static final int DESIRED_KEY_LEN = 255;
    private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
    private static final String KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String PASSWORD_DELIMITER = "$";

    private Cryptographer() {}

    public static boolean check(String password, String stored) {
        String[] saltAndPassword = stored.split("\\$");
        if (saltAndPassword.length != 2) {
            log.log(Level.WARN, "There is a wrong password in a database!");
            return false;
        }
        Optional<String> hash = hash(password, Base64.getDecoder().decode(saltAndPassword[0]));
        return hash.map(s -> s.equals(saltAndPassword[1])).orElse(false);
    }

    public static Optional<String> getSaltedHash(String password) {
        try {
            byte[] salt = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM).generateSeed(SALT_LEN);
            Optional<String> hash = hash(password, salt);
            return hash.map(h -> Base64.getEncoder().encodeToString(salt) + PASSWORD_DELIMITER + h);
        } catch (NoSuchAlgorithmException e) {
            log.log(Level.ERROR, "ERROR: SecureRandom does not support this algorithm: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    private static Optional<String> hash(String password, byte[] salt) throws IllegalArgumentException {
        //todo: validate before using
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(
                    password.toCharArray(), salt, ITERATIONS, DESIRED_KEY_LEN
            ));
            return Optional.of(Base64.getEncoder().encodeToString(key.getEncoded()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.log(Level.ERROR, "ERROR: SecretKeyFactory does not support this algorithm: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }
}
