package com.guiwuu.concurrent.counter;

/**
 *
 * @author guiwuu
 */
public interface Counter {
    
    public int incrementAndGet();
    
    public int addAndGet(int num);
    
    public int get();
    
}
