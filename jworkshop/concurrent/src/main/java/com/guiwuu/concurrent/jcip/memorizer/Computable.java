package com.guiwuu.concurrent.jcip.memorizer;

/**
 *
 * @author jcip
 */
public interface Computable<A, V> {

    public V compute(A arg) throws InterruptedException;
}
