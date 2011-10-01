package com.guiwuu.jpractice.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * A message digest utility, java.security.MessageDigest and javax.crypto.Mac wrapped.
 * Some codes come from javaeye, http://www.javaeye.com/wiki/security/1710-one-way-encryption-algorithm
 * If you want to crack md5 digest, visit http://www.md5.com.cn/
 * 
 * @author guiwuu
 * @version 2010-09-21
 */
public class DigestUtil {

    final public static String MD5 = "MD5";
    final public static String SHA = "SHA1";
    /** 
     * MAC Algorithms: HmacMD5, HmacSHA1, HmacSHA256, HmacSHA384, HmacSHA512
     */
    final public static String HMAC = "HmacMD5";

    /**
     * Return a 32 length MD5 digest String
     * 
     * @param s
     * @return
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    public static String md5_32(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytes = convertToBytes(s);
        byte[] digestedBytes = digest(bytes, MD5);
        return encodeToHex(digestedBytes, 0, 0);
    }

    /**
     * Return a 16 length MD5 digest String
     * 
     * @param s
     * @return
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    public static String md5_16(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytes = convertToBytes(s);
        byte[] digestedBytes = digest(bytes, MD5);
        return encodeToHex(digestedBytes, 4, 4);
    }

    /**
     * Return a 40 length SHA-1 digest String
     * 
     * @param s
     * @return
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    public static String sha(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytes = convertToBytes(s);
        byte[] digestedBytes = digest(bytes, SHA);
        return encodeToHex(digestedBytes, 0, 0);
    }

    /**
     * Return a fixed length hmac digest String
     * 
     * @param s
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws IOException 
     * @throws InvalidKeyException 
     */
    public static String hmac(String s, String key) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        byte[] bytes = convertToBytes(s);
        byte[] digestedBytes = hmac(bytes, key);
        return encodeToHex(digestedBytes, 0, 0);
    }

    /**
     * Initialize a BASE64 encoded HMAC key, using HmacMD5 algorithm
     * 
     * @return
     * @throws Exception
     */
    public static String initHmacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(HMAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64Util.encode(secretKey.getEncoded());
    }

    /**
     * Use a private key to digest and return a fixed length byte[]
     * 
     * @param bytes
     * @param key
     * @return
     * @throws IOException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    public static byte[] hmac(byte[] bytes, String key) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec(Base64Util.decode(key), HMAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(bytes);

    }

    /**
     * Convert String to a char[], using utf-8 charset
     * 
     * @param s
     * @return
     * @throws UnsupportedEncodingException 
     */
    private static byte[] convertToBytes(String s) throws UnsupportedEncodingException {
        byte[] bytes = s.getBytes("utf-8");
        return bytes;
    }

    /**
     * Convert a byte[] to a hex char String
     * 
     * @param bytes
     * @param offset
     * @param tail
     * @return
     */
    private static String encodeToHex(byte[] bytes, int offset, int tail) {
        StringBuilder hexValue = new StringBuilder();

        // bit-wise AND that byte[] with 0xff, prepend "0" to the output
        // StringBuffer to make sure that we don't end up with something like
        // "e21ff" instead of "e201ff"
        for (int i = offset; i < bytes.length - tail; i++) {
            int val = ((int) bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * Digest a byte[] and return a fixed length byte[] by given algorithm, like md5, sha
     * 
     * @param bytes
     * @return
     * @throws NoSuchAlgorithmException 
     */
    private static byte[] digest(byte[] bytes, String algorithm) throws NoSuchAlgorithmException {
        byte[] digestBytes = {};
        MessageDigest md5 = MessageDigest.getInstance(algorithm);
        digestBytes = md5.digest(bytes);
        return digestBytes;
    }
}