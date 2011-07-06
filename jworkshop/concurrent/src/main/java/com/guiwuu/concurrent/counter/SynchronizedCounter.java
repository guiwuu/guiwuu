package com.guiwuu.concurrent.counter;

/**
 *
 * @author guiwuu
 */
public class SynchronizedCounter implements Counter{
    
    private int counter = 0;

    @Override
    synchronized public int incrementAndGet() {
        return ++counter;
    }

    @Override
    synchronized public int addAndGet(int num) {
        this.counter+=num;
        return counter;
    }

    @Override
    public int get() {
        return counter;
    }
    
}
