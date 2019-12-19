package com.huangttt18.concurrency;

/**
 * @author Daniel 2019/12/19
 */
public class ThreadLocalDemo {

    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    static void print(String str) {
        // 从threadLocal中获取变量
        System.out.println(str + ":" + threadLocal.get());
        // 从threadLocal中移除变量
        threadLocal.remove();
    }

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            // 给thread中的threadLocal设置变量
            threadLocal.set("ThreadOne local variable");
            // 打印
            print("Thread One");
            System.out.println("ThreadOne remove after" + ":" + threadLocal.get());
        });

        Thread thread2 = new Thread(() -> {
            // 给thread2中的threadLocal设置变量
            threadLocal.set("ThreadTwo local variable");
            print("Thread Two");
            System.out.println("ThreadTwo remove after" + ":" + threadLocal.get());
        });

        // 启动线程
        thread.start();
        thread2.start();
    }
}
