package com.guiwuu.jpractice.conc.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BatchExecuteThread extends Thread {

    private static final Logger logger = Logger.getLogger(BatchExecuteThread.class.getName());
    private AtomicInteger success;
    private CountDownLatch begin;
    private CountDownLatch end;

    public BatchExecuteThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            begin.await();
            if (runTask()) {
                success.incrementAndGet();
            }
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, "{0} thread is interrupted!", super.getName());
            return;
        } catch (Exception e) {
            logger.log(Level.WARNING, super.getName() + " thread exception occurs: ", e);
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