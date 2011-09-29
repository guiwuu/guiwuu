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

    CacheProviderType provider() default CacheProviderType.LOCAL;

    String namespace() default "";

    CacheSwapPolicy swap() default CacheSwapPolicy.STUPID;

    int init() default 0;

    int max() default Integer.MAX_VALUE;

    int expireTime() default 0;
}
