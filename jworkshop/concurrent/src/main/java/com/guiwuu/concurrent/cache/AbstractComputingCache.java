package com.guiwuu.concurrent.cache;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guiwuu
 */
public abstract class AbstractComputingCache<K, V> implements ComputingCache<K, V> {

    private static final Logger logger = Logger.getLogger(DummyComputingCache.class.getName());
    protected ConcurrentMap<K, Future<V>> storage = new ConcurrentHashMap<K, Future<V>>();
    protected Computable<K, V> c;
    protected volatile int maxSize = 1000;
    protected volatile int swapSize = 100;
    private AtomicInteger access = new AtomicInteger();
    private AtomicInteger fail = new AtomicInteger();

    public AbstractComputingCache(Computable c) {
        this.c = c;
    }

    public V compute(final K input) throws Exception {
        access.incrementAndGet();

        if (input == null) {
            return null;
        }

        while (true) {
            Future<V> f = storage.get(input);
            if (f == null) {
                FutureTask<V> ft = new FutureTask<V>(new Callable<V>() {

                    public V call() throws Exception {
                        return c.compute(input);
                    }
                });

                if (size() > maxSize) {
                    swap();
                }

                f = storage.putIfAbsent(input, ft);
                if (f == null) {
                    fail.incrementAndGet();
                    f = ft;
                    ft.run();
                }
            }

            try {
                return f.get();
            } catch (InterruptedException ex) {
                logger.log(Level.SEVERE, "interrupt exception occurs", ex);
                storage.remove(input);
            } catch (ExecutionException ex) {
                logger.log(Level.SEVERE, "execution exception occurs", ex);
                storage.remove(input);
            }
        }
    }

    public void remove(K input) {
        storage.remove(input);
    }

    public abstract void swap();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getSwapSize() {
        return swapSize;
    }

    public void setSwapSize(int swapSize) {
        this.swapSize = swapSize;
    }

    @Override
    public String toString() {
        BigDecimal accessNum = BigDecimal.valueOf(access.get());
        BigDecimal failNum = BigDecimal.valueOf(fail.get());
        BigDecimal successNum = accessNum.subtract(failNum);
        BigDecimal ratio = successNum.divide(accessNum).setScale(4, RoundingMode.DOWN);
        Object[] params = {maxSize, accessNum, successNum, failNum, ratio};
        return String.format("max: %s, access: %s, success: %s, fail: %s, ratio: %s", (Object[]) params);
    }
}
