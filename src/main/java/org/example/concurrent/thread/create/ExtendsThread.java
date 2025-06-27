package org.example.concurrent.thread.create;

/**
 * ExtendsThread 类继承 Thread 并重写了 run() 方法
 */
public class ExtendsThread extends Thread {
    @Override
    public void run() {
        System.out.println("\nCurrent Thread ID:" + Thread.currentThread().threadId());
        System.out.println("Start a New Thread");
    }

    public static void main(String[] args) {
        System.out.println("Main Thread ID:" + Thread.currentThread().threadId());
        new ExtendsThread().start();
    }
}
