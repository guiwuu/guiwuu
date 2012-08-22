package com.guiwuu.swissknife.converter.jumd;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * UmdParserTest
 *
 * @author daijun
 * @since 2011-02-12
 * @version 2011-02-22
 */
public class UmdUtilTest {

    @Test
    public void testBytesToInt() {
        System.out.println("bytesToInt");
        byte[] bytes = {(byte) 0x96, (byte) 0x76, (byte) 0xc6, (byte) 0xfd};
        int expResult = 0xfdc67696;
        int result = UmdUtil.bytesToInt(bytes);
        assertEquals(expResult, result);
    }

    @Test
    public void testIntToBytes() {
        System.out.println("intToBytes");
        int i = 13215;
        byte[] expResult = {(byte) 0x9f, (byte) 0x33, 0, 0};
        byte[] result = UmdUtil.intToBytes(i);
        assertEquals(expResult.length, result.length);
        for (int j = 0; j < expResult.length; j++) {
            assertEquals(expResult[j], result[j]);
        }
    }

    @Test
    public void testUnicode_String() throws Exception {
        System.out.println("unicode");
        String s = "中国";
        byte[] expResult = s.getBytes("utf-16le");
        byte[] result = UmdUtil.unicode(s);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testUnicode_byteArr() throws Exception {
        System.out.println("unicode");
        String expResult = "测试abc123";
        String result = UmdUtil.unicode(expResult.getBytes("utf-16le"));
        assertEquals(expResult, result);
    }
}