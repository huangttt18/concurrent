package com.huangttt18.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * @author Daniel 2019/12/31
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }, "Thread-2");

        t1.start();
        t2.start();

        Thread t3 = new Thread(() -> {
            try {
                countDownLatch.await();
                System.out.println("Thread-3 从await()方法返回了");
            } catch (InterruptedException e) {
                System.out.println("Thread-3 await()方法被中断了");
                Thread.currentThread().interrupt();
            }
        }, "Thread-3");

        Thread t4 = new Thread(() -> {
            try {
                countDownLatch.await();
                System.out.println("Thread-4 从await()方法返回了");
            } catch (InterruptedException e) {
                System.out.println("Thread-4 await()方法被中断了");
                Thread.currentThread().interrupt();
            }
        }, "Thread-4");

        t3.start();
        t4.start();
    }
}
