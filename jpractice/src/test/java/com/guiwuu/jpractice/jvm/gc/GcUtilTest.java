package com.guiwuu.jpractice.jvm.gc;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

public class GcUtilTest {

    private final Random r = new Random();

    @Test
    public void runSizeOfString() throws Exception {
        SizeOf obj = new SizeOf() {

            public Object newInstance() {
                int len = 1000;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < len; i++) {
                    sb.append(r.nextInt(9));
                }
                return sb.toString();
            }
        };

        System.out.println("String占用内存：" + GcUtil.sizeOf(obj, 10000) + "Bytes");
    }

    @Test
    public void runSizeOfConcurrentHashMap() throws Exception {
        SizeOf obj = new SizeOf() {

            public Object newInstance() {
                int len = 1000;
                Map<String, String> map = new ConcurrentHashMap<String, String>(
                        len);
                for (int i = 0; i < len; i++) {
                    StringBuilder key = new StringBuilder();
                    for (int j = 0; j < 10; j++) {
                        key.append(r.nextInt(9));
                    }

                    StringBuilder value = new StringBuilder();
                    for (int j = 0; j < 10; j++) {
                        value.append(r.nextInt(9));
                    }

                    map.put(key.toString(), value.toString());
                }
                return map;
            }
        };

        System.out.println("ConcurrentHashMap占用内存：" + GcUtil.sizeOf(obj, 1000) + "Bytes");
    }
}
