package com.guiwuu.concurrent.queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author guiwuu
 */
public class LockConditionBlockingQueue<E> implements BlockingQueue<E> {

    private int maxLength = 10;
    private Queue<E> q = new LinkedList<E>();
    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public LockConditionBlockingQueue(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public E take() throws InterruptedException {
        lock.lock();
        try {
            if (q.isEmpty()) {
                notEmpty.await();
            }
            if (q.size() == maxLength) {
                notFull.signalAll();
            }
            return q.poll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void put(E e) throws InterruptedException {
        lock.lock();
        try {
            if (q.isEmpty()) {
                notEmpty.signalAll();
            }
            if (q.size() == maxLength) {
                notFull.await();
            }
            q.add(e);
        } finally {
            lock.unlock();
        }
    }
}
