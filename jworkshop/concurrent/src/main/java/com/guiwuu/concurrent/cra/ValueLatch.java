package com.guiwuu.concurrent.cra;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author daijun
 */
public class ValueLatch<T> {

    private T value = null;// guardedby(this)
    private final CountDownLatch done = new CountDownLatch(1);

    public boolean isSet() {
        return (done.getCount() == 0);
    }

    // modification visible
    public synchronized void setValue(T newValue) {
        if (!isSet()) {
            value = newValue;
            done.countDown();
        }
    }

    // read visible
    public T getValue() throws InterruptedException {
        done.await();
        synchronized (this) {
            return value;
        }
    }
}
