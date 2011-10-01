package com.guiwuu.jpractice.conc.cache;

/**
 *
 * @author guiwuu
 */
public interface ComputingCache<K, V> {

    public V compute(final K key) throws Exception;

    public void removeFromStorage(K key);

    public void swap();

    public int size();

    public void clear();

    public int getMaxSize();

    public void setMaxSize(int MaxSize);

    public int getSwapSize();

    public void setSwapSize(int SwapSize);
}
