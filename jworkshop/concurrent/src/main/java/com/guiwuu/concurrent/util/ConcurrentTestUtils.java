package com.guiwuu.concurrent.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcurrentTestUtils {

    private static final Logger logger = Logger.getLogger(ConcurrentTestUtils.class.getName());

    public static int run(BatchExecuteThread[] threads) throws Exception {
        int concurrent = threads.length;
        AtomicInteger success = new AtomicInteger();
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(concurrent);
        long beginTime = System.currentTimeMillis();

        for (BatchExecuteThread t : threads) {
            t.setBegin(begin);
            t.setEnd(end);
            t.setSuccess(success);
            t.start();
        }

        begin.countDown();
        end.await();
        long endTime = System.currentTimeMillis();
        long totalCost = endTime - beginTime;
        logger.log(Level.WARNING, "total cost: {0}ms", totalCost);
        return success.get();
    }

    public static int run(CyclicExecuteThread[] threads, int loop) throws Exception {
        int concurrent = threads.length;
        AtomicInteger success = new AtomicInteger();
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(concurrent * loop);
        CyclicBarrier barrier = new CyclicBarrier(loop);
        long beginTime = System.currentTimeMillis();

        for (CyclicExecuteThread t : threads) {
            t.setBegin(begin);
            t.setEnd(end);
            t.setBarrier(barrier);
            t.setSuccess(success);
            t.start();
        }

        begin.countDown();
        end.await();
        long endTime = System.currentTimeMillis();
        long totalCost = endTime - beginTime;
        logger.log(Level.WARNING, "total cost: {0}ms", totalCost);
        return success.get();
    }
}