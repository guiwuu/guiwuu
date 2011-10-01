package com.guiwuu.jpractice.conc.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author guiwuu
 */
public class UnboundedLinkedBlockingQueue<E> implements BlockingQueue<E> {

    private final Object notEmpty = new Object();
    private Queue<E> q = new LinkedList<E>();

    @Override
    public E take() throws InterruptedException {
        synchronized (notEmpty) {
            if (q.isEmpty()) {
                notEmpty.wait();
            }
            return q.poll();
        }
    }

    @Override
    public void put(E e) throws InterruptedException {
        synchronized (notEmpty) {
            if (q.isEmpty()) {
                notEmpty.notifyAll();
            }
            q.add(e);
        }
    }
}
