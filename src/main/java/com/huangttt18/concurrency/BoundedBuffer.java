package com.huangttt18.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Daniel 2019/12/31
 */
public class BoundedBuffer {

    final Lock lock = new ReentrantLock();

    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putPtr, takePtr, count;

    // produce
    public void put(Object o) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[putPtr] = o;
            if (++putPtr == items.length) {
                putPtr = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    // consume
    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            Object o = items[takePtr];
            if (++takePtr == items.length) {
                takePtr = 0;
            }
            --count;
            notFull.signal();
            return o;
        } finally {
            lock.unlock();
        }
    }
}
