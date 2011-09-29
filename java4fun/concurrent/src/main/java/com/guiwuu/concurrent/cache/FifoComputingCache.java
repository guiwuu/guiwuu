package com.guiwuu.concurrent.cache;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author guiwuu
 */
public class FifoComputingCache<K, V> extends AbstractComputingCache<K, V> {

    private Queue<K> queue = new ConcurrentLinkedQueue<K>();

    public FifoComputingCache(final Computable<K, V> c) {
        super(c);
        Computable cc = new Computable<K, V>() {

            @Override
            public V compute(K input) throws Exception {
                queue.add(input);
                return c.compute(input);
            }
        };
        this.c = cc;
    }

    @Override
    public void swap() {
        for (int i = 0; i < swapSize && size() + swapSize > maxSize; i++) {
            K input = queue.remove();
            removeFromStorage(input);
        }
    }
}
