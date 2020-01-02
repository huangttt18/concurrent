package com.huangttt18.concurrency.deadlock.notify;

import java.util.ArrayList;
import java.util.List;

/**
 * 临界区"管理员"：掌管线程所需要的所有资源
 *
 * <p>
 * allocator需为单例，这里使用静态内部类实现单例
 * </p>
 *
 * @author Daniel 2020/1/2
 */
public class Allocator {

    private List<Object> als = new ArrayList<>();

    private Allocator() {
    }

    public static Allocator getInstance() {
        return SingletonGenerator.INSTANCE;
    }

    /**
     * 一次申请所有的资源
     */
    public synchronized void apply(Object from, Object to) {
        while (als.contains(from) || als.contains(to)) {
            // 如果管理员只持有一个资源，则申请失败
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        als.add(from);
        als.add(to);
    }

    /**
     * 释放资源
     */
    public synchronized void free(Object from, Object to) {
        als.remove(from);
        als.remove(to);
        notifyAll();
    }

    private static class SingletonGenerator {
        private static final Allocator INSTANCE = new Allocator();
    }
}
