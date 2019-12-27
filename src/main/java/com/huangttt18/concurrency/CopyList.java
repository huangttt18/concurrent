package com.huangttt18.concurrency;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Daniel 2019/12/27
 */
public class CopyList {

    private static volatile CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        arrayList.add("hello");
        arrayList.add("alibaba");
        arrayList.add("welcome");
        arrayList.add("to");
        arrayList.add("hangzhou");
        Thread threadOne = new Thread(() -> {
            arrayList.set(1, "baba");
            arrayList.remove(2);
            arrayList.remove(3);
        });
        Iterator<String> iterator = arrayList.iterator();
        threadOne.start();
        threadOne.join();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
