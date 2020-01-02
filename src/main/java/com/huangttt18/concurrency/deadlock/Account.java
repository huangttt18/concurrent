package com.huangttt18.concurrency.deadlock;

/**
 * 死锁发生的条件
 *              (1):互斥：多线程之间存在多个共享资源
 *              (2):占用且等待：各个线程对自己持有的共享资源保持不释放，并且在等待其他共享资源时不释放自己的共享资源
 *              (3):不可抢占：各个线程所持有的共享资源不会被其他线程强制占有
 *              (4):循环等待：多线程之间在等待其他线程释放资源
 * 如何解决死锁问题
 *              破坏死锁的条件
 *              针对(2)，可以让线程一开始就获取到需要的所有资源
 *              针对(3)，如果线程在持有共享资源的情况下未获取到另外一个想要持有的资源，则主动释放当前已经获取的共享资源
 *              针对(4)，可以按照顺序来进行申请，给资源标号，从小到大，依次申请
 * @author Daniel 2020/1/2
 */
public class Account {

    private Allocator allocator = Allocator.getInstance();
    private int balance;

    public void transfer(Account target, int amount) {
        // 首先申请两个账户锁（共享资源）
        while (!(allocator.apply(this, target))) {
            System.out.println("申请资源失败");
        }

        try {
            // 锁定from
            synchronized (this) {
                // 锁定to
                synchronized (target) {
                    if (this.balance >= amount) {
                        this.balance -= amount;
                        target.balance += amount;
                    }
                }
            }
        } finally {
            // 释放资源
            allocator.free(this, target);
        }
    }
}
