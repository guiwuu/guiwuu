package com.guiwuu.jpractice.conc.cache;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author guiwuu
 */
public class LruComputingCache<K, V> extends AbstractComputingCache<K, V> {

    private Queue<K> queue = new ConcurrentLinkedQueue<K>();

    public LruComputingCache(final Computable<K, V> c) {
        super(c);
        Computable cc = new Computable<K, V>() {

            @Override
            public V compute(K input) throws Exception {
                if (!queue.contains(input)) {
                    queue.add(input);
                } else {
                    queue.remove(input);
                    queue.add(input);
                }
                return c.compute(input);
            }
        };
        this.c = cc;
    }

    @Override
    public void swap() {
        int swap = 0;
        while (swap < swapSize && size() + swapSize > maxSize) {
            K input = queue.remove();
            removeFromStorage(input);
        }
    }
}
