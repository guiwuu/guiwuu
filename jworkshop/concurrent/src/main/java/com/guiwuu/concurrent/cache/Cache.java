package com.guiwuu.concurrent.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author guiwuu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {

    CacheProvider provider();

    String namespace();

    CacheSwap swap();

    int init();

    int max();

    long expireTime();
}
