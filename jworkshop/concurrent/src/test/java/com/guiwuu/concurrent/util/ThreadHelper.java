package com.guiwuu.concurrent.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;

public abstract class ThreadHelper extends Thread {

    private static final Logger logger = Logger.getLogger(ThreadHelper.class);
    private AtomicInteger success;
    private CountDownLatch begin;
    private CountDownLatch end;

    public ThreadHelper(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            begin.await();
            if (runTask()) {
                success.incrementAndGet();
            }
        } catch (Exception e) {
            logger.error(getName() + " exception occurs!", e);
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