package com.guiwuu.concurrent.cache;

import java.util.Iterator;

/**
 *
 * @author guiwuu
 */
public class DummyComputingCache<K, V> extends AbstractComputingCache<K, V> {

    public DummyComputingCache(Computable<K, V> c) {
        super(c);
    }

    @Override
    public void swap() {
        int swap = 0;
        Iterator<K> it = storage.keySet().iterator();
        while(swap < swapSize && size() + swapSize > maxSize && it.hasNext()){
            swap++;
            remove(it.next());
        }
    }
}
