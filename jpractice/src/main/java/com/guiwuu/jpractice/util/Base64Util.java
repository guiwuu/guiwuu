package com.guiwuu.jpractice.util;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {

    /**
     * BASE64 encoding
     * 
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) {
        return (new BASE64Encoder()).encodeBuffer(bytes);
    }

    /**
     * BASE64 decoding
     * 
     * @param s
     * @return
     * @throws IOException 
     */
    public static byte[] decode(String s) throws IOException {
        return (new BASE64Decoder()).decodeBuffer(s);
    }
}
