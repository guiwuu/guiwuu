package com.guiwuu.concurrent.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ThreadWrapper extends Thread {

    private static final Logger logger = Logger.getLogger(ThreadWrapper.class.getName());
    private AtomicInteger success;
    private CountDownLatch begin;
    private CountDownLatch end;
    protected int num;
    
    public ThreadWrapper(String name) {
        super(name);
    }

    public ThreadWrapper(String name, int num) {
        super(name);
        this.num = num;
    }

    @Override
    public void run() {
        try {
            begin.await();
            if (runTask()) {
                success.incrementAndGet();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, " exception occurs!", e);
        } finally {
            end.countDown();
        }
    }

    protected abstract boolean runTask() throws Exception;

    public void setSuccess(AtomicInteger success) {
        this.success = success;
    }

    public void setBegin(CountDownLatch begin) {
        this.begin = begin;
    }

    public void setEnd(CountDownLatch end) {
        this.end = end;
    }
}