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
public class BoundedCache<K, V> {

    private static final Logger logger = Logger.getLogger(BoundedCache.class.getName());
    private final ConcurrentMap<K, Future<V>> storage = new MapMaker().softKeys().makeMap();
    private int init = 1000;
    private int max = init;
    private AtomicInteger access = new AtomicInteger();
    private AtomicInteger fail = new AtomicInteger();
    
    public BoundedCache(){
    }
    
    public BoundedCache(int init){
        this.init = init;
        this.max = init;
    }

    public BoundedCache(int init, int max) {
        this.init = init;
        this.max = max;
    }

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

    public V put(K key, FutureTask<V> value) {
        if (storage.size() > max) {// if full swap them all out
            storage.clear();
        }

        Future<V> f = storage.putIfAbsent(key, value);
        if (f == null) {// new element
            f = value;
            value.run();
        }
        return handleException(key, f);
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

    @Override
    public String toString() {
        BigDecimal accessNum = BigDecimal.valueOf(access.get());
        BigDecimal failNum = BigDecimal.valueOf(fail.get());
        BigDecimal successNum = accessNum.subtract(failNum);
        BigDecimal ratio = successNum.divide(accessNum).setScale(4, RoundingMode.DOWN);
        Object[] params = {init, max, accessNum, successNum, failNum, ratio};
        return String.format("init: %s, max: %s, access: %s, success: %s, fail: %s, ratio: %s", (Object[]) params);
    }
}
