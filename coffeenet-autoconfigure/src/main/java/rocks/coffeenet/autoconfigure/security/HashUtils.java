package rocks.coffeenet.autoconfigure.security;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Simple static helpers for generating cryptographic hashes from input.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public final class HashUtils {

    private HashUtils() {
    }

    public static String sha256hex(String s) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                "Couldn't get an instance of the SHA-256 digest, this should never happen.", e);
        }
    }
}
