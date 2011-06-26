package com.guiwuu.concurrent.cache;

import java.util.concurrent.FutureTask;

/**
 *
 * @author guiwuu
 */
public interface BoundedCache<K, V> {
    
    public V compute(K input);

    public V get(K key);

    public V put(K key, FutureTask<V> value);
    
    public V remove(K key);

    public int size();

    public void clear();

    public int getInitSize();

    public void setInitSize(int initSize);

    public int getMaxSize();

    public void setMaxSize(int MaxSize);

    public int getSwapSize();

    public void setSwapSize(int SwapSize);
}
