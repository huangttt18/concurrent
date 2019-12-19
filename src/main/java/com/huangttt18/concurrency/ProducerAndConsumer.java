package com.huangttt18.concurrency;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 生产者与消费者
 *
 * @author Daniel 2019/12/19
 */
public class ProducerAndConsumer {

    private static final LinkedBlockingDeque<Integer> queue;

    private static final int MAX_SIZE = 20;
    private static final int MAX_PRODUCER = 10;
    private static final int MAX_CONSUMER = 10;
    private static int COUNT = new Random().nextInt() % 10000;

    static {
        queue = new LinkedBlockingDeque<>(MAX_SIZE);
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        for (int i = 0; i < MAX_PRODUCER; i++) {
            new Thread(producer, "生产者线程 - " + i).start();
        }

        for (int i = 0; i < MAX_CONSUMER; i++) {
            new Thread(consumer, "消费者线程 - " + i).start();
        }
    }

    /**
     * 生产者线程
     */
    public static class Producer implements Runnable {

        public void run() {
            // 使用synchronized关键字，能获取到对象的monitor监视器锁
            synchronized (queue) {
                // 消费队列满，则等待队列空闲
                while (queue.size() == MAX_SIZE) {
                    // 挂起当前线程，并释放同步锁，让消费者线程可以获取该锁并消费队列中的元素
                    try {
                        System.out.println("队列已满，生产线程阻塞.");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 如果队列不满，则可以继续添加元素，并通知消费者线程
                queue.add(COUNT);
                System.out.printf("生产者线程生产了 %d 号元素.\n", COUNT);
                queue.notifyAll();
            }
        }
    }

    /**
     * 消费者线程
     */
    public static class Consumer implements Runnable {

        public void run() {
            synchronized (queue) {
                while (queue.size() == 0) {
                    // 当消费队列为空时，挂起当前线程，并释放锁，让生产者线程可以获取锁并放入元素到队列
                    try {
                        System.out.println("队列已空，消费线程阻塞.");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 如果队列还有元素，则继续消费，同时可以让生产者线程添加元素
                try {
                    Integer ele = queue.take();
                    System.out.printf("消费者线程消费了 %d 号元素.\n", ele);
                    queue.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
