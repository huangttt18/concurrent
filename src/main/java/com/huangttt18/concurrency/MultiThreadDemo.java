package com.huangttt18.concurrency;

/**
 * @author Daniel 2019/12/23
 */
public class MultiThreadDemo {

    private static int num = 0;
    private static boolean ready = false;

    public static class ReadThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (ready) {
                    System.out.println(num + num);
                }
                System.out.println("Thread read!");
            }
        }
    }

    public static class WriteThread extends Thread {
        @Override
        public void run() {
            num = 2;
            ready = true;
            System.out.println("WriteThread set over");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadThread readThread = new ReadThread();
        readThread.start();
        WriteThread writeThread = new WriteThread();
        writeThread.start();
        Thread.sleep(10);
        readThread.interrupt();
        System.out.println("main exit");
    }
}
