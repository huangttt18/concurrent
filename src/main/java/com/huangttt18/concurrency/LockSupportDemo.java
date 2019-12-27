package com.huangttt18.concurrency;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Daniel 2019/12/27
 */
public class LockSupportDemo {

    public static void main(String[] args) {
        System.out.println("Begin");
        LockSupport.park(Thread.currentThread());
        System.out.println("Middle");
        LockSupport.park(Thread.currentThread());
        System.out.println("End");
    }
}
