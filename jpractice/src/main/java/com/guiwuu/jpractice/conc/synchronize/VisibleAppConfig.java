package com.guiwuu.jpractice.conc.synchronize;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Application configuration with visible properties
 *
 * @author diancang
 * @since 2012-05-26
 */
public class VisibleAppConfig implements AppConfig{
    public volatile int i = 0;
    public AtomicInteger j = new AtomicInteger(0);
    private boolean updated = false;

    @Override
    public void update() {
        i++;
        j.incrementAndGet();
        updated = true;
    }

    @Override
    public boolean isUpdated() {
        return updated;
    }
}
