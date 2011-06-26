package com.guiwuu.concurrent.cache;

import com.google.common.collect.MapMaker;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class DummyCache<K, V> implements BoundedCache<K, V> {

    private static final Logger logger = Logger.getLogger(DummyCache.class.getName());
    private final ConcurrentMap<K, Future<V>> storage = new MapMaker().softKeys().makeMap();
    private int initSize = 1000;
    private volatile int maxSize = initSize;
    private AtomicInteger access = new AtomicInteger();
    private AtomicInteger fail = new AtomicInteger();

    @Override
    public V get(K key) {
        access.incrementAndGet();
        Future<V> f = storage.get(key);
        if (f == null) {// cache missing need caller to handle this
            fail.incrementAndGet();
            return null;
        } else {
            return handleException(key, f);
        }
    }

    @Override
    public V put(K key, FutureTask<V> value) {
        if (storage.size() > maxSize) {// if full swap them all out
            storage.clear();
        }

        Future<V> f = storage.putIfAbsent(key, value);
        if (f == null) {// new element
            f = value;
            value.run();
        }
        return handleException(key, f);
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int getInitSize() {
        return initSize;
    }

    @Override
    public void setInitSize(int initSize) {
        this.initSize = initSize;
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public int getSwapSize() {
        return maxSize;
    }

    @Override
    public void setSwapSize(int swapSize) {
        return;
    }

    @Override
    public String toString() {
        BigDecimal accessNum = BigDecimal.valueOf(access.get());
        BigDecimal failNum = BigDecimal.valueOf(fail.get());
        BigDecimal successNum = accessNum.subtract(failNum);
        BigDecimal ratio = successNum.divide(accessNum).setScale(4, RoundingMode.DOWN);
        Object[] params = {initSize, maxSize, accessNum, successNum, failNum, ratio};
        return String.format("init: %s, max: %s, access: %s, success: %s, fail: %s, ratio: %s", (Object[]) params);
    }

    private V handleException(K key, Future<V> f) {
        try {
            return f.get();
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, "interrupt exception occurs", ex);
            storage.remove(key);
            return null;
        } catch (ExecutionException ex) {
            logger.log(Level.SEVERE, "execution exception occurs", ex);
            storage.remove(key);
            return null;
        }
    }
}
