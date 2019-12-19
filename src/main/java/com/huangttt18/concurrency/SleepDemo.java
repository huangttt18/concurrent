package com.huangttt18.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Sleep Demo
 *
 * @author Daniel 2019/12/19
 */
public class SleepDemo {

    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            // 获取独占锁
            lock.lock();
            try {
                System.out.println("Child threadA is sleeping");
                Thread.sleep(1000);
                System.out.println("Child threadA awake");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放独占锁
                lock.unlock();
            }
        }, "线程1");

        Thread threadB = new Thread(() -> {
            // 获取独占锁
            lock.lock();
            try {
                System.out.println("Child threadB is sleeping");
                Thread.sleep(1000);
                System.out.println("Child threadB awake");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放独占锁
                lock.unlock();
            }
        }, "线程2");

        threadA.start();
        threadB.start();
    }
}
