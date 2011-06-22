package com.guiwuu.concurrent.cache;

import com.google.common.collect.MapMaker;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * @author guiwuu
 */
public class LocalCacheProvider {
    
    private final static ConcurrentMap defaultStorage = new MapMaker().softKeys().softValues().makeMap();
    
    private final static ConcurrentMap namespaceStorage = new ConcurrentHashMap<String, ConcurrentMap>();
    
    public static Class aop(Class clazz) {
        Method[] methods = clazz.getMethods();
        for(Method method : methods){
            if (method.isAnnotationPresent(Cache.class)){
                Cache annotation = method.getAnnotation(Cache.class);
            }
        }
        return clazz;
    }
    
}
