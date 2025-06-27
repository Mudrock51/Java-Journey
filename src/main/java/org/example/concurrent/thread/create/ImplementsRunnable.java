package org.example.concurrent.thread.create;

/**
 * 实现 Runnable 接口
 */
public class ImplementsRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("\nCurrent Thread ID:" + Thread.currentThread().threadId());
        System.out.println("Start a New Thread");
    }

    public static void main(String[] args) {
        System.out.println("Main Thread ID:" + Thread.currentThread().threadId());
        ImplementsRunnable implementsRunnable = new ImplementsRunnable();
        new Thread(implementsRunnable).start();
    }
}
