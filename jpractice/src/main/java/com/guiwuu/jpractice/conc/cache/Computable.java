package com.guiwuu.jpractice.conc.cache;

/**
 *
 * @author guiwuu
 */
public interface Computable<K, V> {

    public V compute(K input) throws Exception;
}
