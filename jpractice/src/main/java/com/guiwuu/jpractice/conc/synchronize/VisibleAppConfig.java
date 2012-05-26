package com.guiwuu.jpractice.conc.synchronize;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Application configuration with visible properties
 *
 * @author diancang
 * @since 2012-05-26
 */
public class VisibleAppConfig {
    public volatile int i = 0;
    public AtomicInteger j = new AtomicInteger(0);

    public void update() {
        i++;
        j.incrementAndGet();
    }
}
