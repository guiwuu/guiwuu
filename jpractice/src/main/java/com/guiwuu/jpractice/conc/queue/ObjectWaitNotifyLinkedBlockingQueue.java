package com.guiwuu.jpractice.conc.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author guiwuu
 */
public class ObjectWaitNotifyLinkedBlockingQueue<E> implements BlockingQueue<E> {

    private int maxLength = 10;
    private Queue<E> q = new LinkedList<E>();
    private final Object notEmpty = new Object();
    private final Object notFull = new Object();

    public ObjectWaitNotifyLinkedBlockingQueue(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public E take() throws InterruptedException {
        synchronized (notEmpty) {
            if (q.isEmpty()) {
                notEmpty.wait();
            }
            synchronized (notFull) {
                if (q.size() == maxLength) {
                    notFull.notifyAll();
                }
                return q.poll();
            }
        }
    }

    @Override
    public void put(E e) throws InterruptedException {
        synchronized (notEmpty) {
            if (q.isEmpty()) {
                notEmpty.notifyAll();
            }
            synchronized (notFull) {
                if (q.size() == maxLength) {
                    notFull.wait();
                }
                q.add(e);
            }
        }
    }
}
