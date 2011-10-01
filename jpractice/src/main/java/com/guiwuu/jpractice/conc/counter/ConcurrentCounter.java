package com.guiwuu.jpractice.conc.counter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author guiwuu
 */
public class ConcurrentCounter implements Counter {

    private final AtomicInteger counter = new AtomicInteger();

    @Override
    public int incrementAndGet() {
        return counter.incrementAndGet();
    }

    @Override
    public int addAndGet(int num) {
        return counter.addAndGet(num);
    }

    @Override
    public int get() {
        return counter.get();
    }
}
