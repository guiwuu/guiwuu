package com.guiwuu.jpractice.conc.queue;

/**
 *
 * @author guiwuu
 */
public interface BlockingQueue<E> {

    public E take() throws InterruptedException;

    public void put(E e) throws InterruptedException;
}
