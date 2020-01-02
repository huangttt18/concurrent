package com.huangttt18.concurrency;

/**
 * @author Daniel 2020/1/2
 */
public class SynchronizedDemo {

    static class SafeCalc {
        long value = 0L;

        long get() {
            return value;
        }

        synchronized void increment() {
            value += 1;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SafeCalc safeCalc = new SafeCalc();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                safeCalc.increment();
                System.out.println("Thread 1 invoke add(), value: " + safeCalc.get());
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
//                safeCalc.increment();
                System.out.println("Thread 2 invoke get(), value: " + safeCalc.get());
            }
        }, "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println(safeCalc.get());
    }
}
