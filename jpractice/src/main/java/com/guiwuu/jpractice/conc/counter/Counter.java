package com.guiwuu.jpractice.conc.counter;

/**
 *
 * @author guiwuu
 */
public interface Counter {
    
    public int incrementAndGet();
    
    public int addAndGet(int num);
    
    public int get();
    
}
