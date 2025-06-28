package org.example.concurrent.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁 ReentrantLockDemo
 */
public class ReentrantLockDemo {
    Thread t = new Thread() {
        @Override
        public void run() {
            ReentrantLock lock = new ReentrantLock();

            // 1、第一次尝试获取锁，输出锁的重入次数
            lock.lock();
            System.out.println("lock(): lock count :" + lock.getHoldCount());

            // 2、中断当前线程
            interrupt();
            System.out.println("Current Thread is intrupted");

            // 3.1、尝试获取锁
            lock.tryLock();
            // 3.2、锁的重入次数为 2
            System.out.println("trylock() on intrupted thread lock count: " + lock.getHoldCount());

            try {
                // 4、打印线程的中断状态为 True, 调用 lockInterruptibly() 抛出中断异常
                System.out.println("Current Thread is intrupted: " + Thread.currentThread().isInterrupted());
                lock.lockInterruptibly();
                System.out.println("lockInterruptibly() --Not executable statement" + lock.getHoldCount());
            } catch (InterruptedException e) {
                lock.lock();
                System.out.println("Error");
            } finally {
                lock.unlock();
            }

            // 5、打印锁的重入次数，可以发现 lockInterruptibly() 没有成功获取锁
            System.out.println("lockInterruptibly() not able to Accquire lock:  lock count: " + lock.getHoldCount());

            lock.unlock();
            System.out.println("lock count: " + lock.getHoldCount());
            lock.unlock();
            System.out.println("lock count: " + lock.getHoldCount());
        }
    };

    public static void main(String[] args) {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        reentrantLockDemo.t.start();
    }
}
