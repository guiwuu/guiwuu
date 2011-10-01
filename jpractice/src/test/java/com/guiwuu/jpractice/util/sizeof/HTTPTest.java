package com.guiwuu.jpractice.util.sizeof;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URLDecoder;

import org.junit.Test;

public class HTTPTest {

    @Test
    public void testHttpPost() throws Exception {
        Thread.currentThread().setName("wahaha");
        BufferedReader reader = new BufferedReader(new FileReader("D:\\Downloads\\12345.TXT"));
        String param = reader.readLine();
        param = URLDecoder.decode(param, "utf-8");
        System.out.println(param.getBytes().length);
    }
}
