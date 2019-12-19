package com.huangttt18.concurrency;

/**
 * Join Demo
 * @author Daniel 2019/12/19
 */
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Child thread one over!");
        }, "线程1");

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Child thread two over!");
        }, "线程2");

        thread.start();
        thread2.start();
        System.out.println("Wait all child thread over!");
        thread.join();
        thread2.join();
        System.out.println("All child thread over!");
    }
}
