/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guiwuu.concurrent.cache;

/**
 *
 * @author daijun
 */
public interface Computable<A, V> {

    public V compute(A arg) throws InterruptedException;
}
