package com.guiwuu.concurrent.cache;

/**
 *
 * @author guiwuu
 */
public interface Computable<K, V> {

    public V compute(K input) throws Exception;
}
