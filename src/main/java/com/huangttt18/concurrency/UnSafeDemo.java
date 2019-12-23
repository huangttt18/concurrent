package com.huangttt18.concurrency;

import sun.misc.Unsafe;

/**
 * @author Daniel 2019/12/20
 */
public class UnSafeDemo {

    static final Unsafe unSafe = Unsafe.getUnsafe();

    static final long stateOffset;

    private volatile long state = 0;

    static {
        try {
            stateOffset = unSafe.objectFieldOffset(UnSafeDemo.class.getDeclaredField("state"));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        UnSafeDemo unSafeDemo = new UnSafeDemo();
        boolean success = unSafe.compareAndSwapInt(unSafeDemo, stateOffset, 0, 1);
        System.out.println(success);
    }
}
