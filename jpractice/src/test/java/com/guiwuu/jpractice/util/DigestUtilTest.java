package com.guiwuu.jpractice.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DigestUtilTest {

    @Test
    public void testMD5() throws Exception {
        assertEquals("202cb962ac59075b964b07152d234b70", DigestUtil.md5_32("123"));
        assertEquals("c13dceabcb143acd6c9298265d618a9f", DigestUtil.md5_32("中国"));

        assertEquals("ac59075b964b0715", DigestUtil.md5_16("123"));
        assertEquals("cb143acd6c929826", DigestUtil.md5_16("中国"));

        assertEquals(DigestUtil.md5_32("中国").length(), DigestUtil.md5_32("123").length());
        assertEquals(DigestUtil.md5_16("中国").length(), DigestUtil.md5_16("123").length());
    }

    @Test
    public void testSHA() throws Exception {
        assertEquals("40bd001563085fc35165329ea1ff5c5ecbdbbeef", DigestUtil.sha("123"));
        assertEquals("101806f57c322fb403a9788c4c24b79650d02e77", DigestUtil.sha("中国"));
        assertEquals(DigestUtil.sha("中国").length(), DigestUtil.sha("123").length());
    }

    @Test
    public void testHMAC() throws Exception {
        String key = DigestUtil.initHmacKey();
        assertEquals(DigestUtil.hmac("123", key), DigestUtil.hmac("123", key));
        assertEquals(DigestUtil.hmac("中国", key), DigestUtil.hmac("中国", key));
        assertEquals(DigestUtil.hmac("123", key).length(), DigestUtil.hmac("中国", key).length());
    }
}
