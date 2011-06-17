package com.guiwuu.concurrent.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

public class ConcurrentTestHelper {

    private static final Logger logger = Logger.getLogger(ConcurrentTestHelper.class);

    public static int run(ThreadHelper[] threads) throws Exception {
        int concurrent = threads.length;
        AtomicInteger success = new AtomicInteger();
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(concurrent);
        long beginTime = System.currentTimeMillis();

        for (ThreadHelper t : threads) {
            t.setBegin(begin);
            t.setEnd(end);
            t.setSuccess(success);
            t.start();
        }

        begin.countDown();
        end.await();
        long endTime = System.currentTimeMillis();
        logger.warn("cost time: " + (endTime - beginTime) + "ms");
        return success.get();
    }
}