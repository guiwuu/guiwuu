package com.guiwuu.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class UTF8UtilTest {

    @Test
    public void testIsUtf8() throws Exception {
        String s = "中国";
        assertFalse(UTF8Util.isUTF8(s.getBytes("iso-8859-1")));
        assertFalse(UTF8Util.isUTF8(s.getBytes("gbk")));
        assertTrue(UTF8Util.isUTF8(s.getBytes("utf-8")));
    }
}
