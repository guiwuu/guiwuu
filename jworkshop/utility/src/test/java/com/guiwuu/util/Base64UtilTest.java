package com.guiwuu.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Base64UtilTest {

    @Test
    public void testBase64() throws Exception {
        String inputStr = "中国123";
        byte[] inputData = inputStr.getBytes();
        String base64 = Base64Util.encode(inputData);
        byte[] outputData = Base64Util.decode(base64);
        String outputStr = new String(outputData);
        assertEquals(inputStr, outputStr);
    }
}
