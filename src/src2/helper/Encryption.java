package src2.helper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Password encoder using XOR and Base64.
 * 
 * @author Chin Linn Sheng
 * @version 1.2
 * @since 2024-11-03
 */
public class Encryption {

    /**
     * Default constructor for Encryption class.
     */
    public Encryption() {
    }

    /**
     * A secret key used for XOR encryption. 
     * This key should remain consistent between encoding and decoding operations.
     */
    private static final int SECRET_KEY = 123;

    /**
     * Encodes a password using XOR and Base64.
     *
     * @param password Password to encode.
     * @return Encoded password.
     */
    public static String encode(String password) {
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] encodedBytes = xor(passwordBytes);
        return Base64.getEncoder().encodeToString(encodedBytes);
    }

    /**
     * Decodes an encoded password using XOR and Base64.
     *
     * @param encodedPassword Encoded password.
     * @return Decoded password.
     */
    public static String decode(String encodedPassword) {
        byte[] encodedBytes = Base64.getDecoder().decode(encodedPassword);
        byte[] passwordBytes = xor(encodedBytes);
        return new String(passwordBytes, StandardCharsets.UTF_8);
    }

    /**
     * Performs XOR operation on two byte arrays.
     *
     * @param bytes Byte array to XOR.
     * @return XORed byte array.
     */
    private static byte[] xor(byte[] bytes) {
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = (byte) (bytes[i] ^ Encryption.SECRET_KEY);
        }
        return result;
    }
}
