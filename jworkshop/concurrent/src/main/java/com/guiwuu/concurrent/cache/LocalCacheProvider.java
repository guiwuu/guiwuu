package com.guiwuu.concurrent.cache;

import com.google.common.collect.MapMaker;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;

/**
 *
 * @author guiwuu
 */
public class LocalCacheProvider<K,V> {
    
    private ConcurrentMap<K, Future<V>> defaultStorage = new MapMaker().softKeys().softValues().makeMap();
    
    private ConcurrentMap<String, ConcurrentMap> namespaceStorage = new ConcurrentHashMap<String, ConcurrentMap>();
    
}
