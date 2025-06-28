package org.example.concurrent.thread.lock;

/**
 * Deadlock 死锁
 *  1. 破坏请求与保持条件：一次性申请所有资源
 *  2. 破坏不剥夺条件：占用部分资源的线程进一步申请其它资源时，如果申请不到就主动释放它占有的资源
 *  3. 破坏循环等待条件：按固定顺序申请资源，逆序释放资源
 * 
 * 使用 JConsole 可以排查死锁: JDK/bin/jconsole.exe
 */
public class DeadLockDemo {
    private static Object o1 = new Object(); // 资源1
    private static Object o2 = new Object(); // 资源2

    public static void thisWillDeadlock() {
        new Thread(() -> {
            synchronized (o1) {
                System.out.println(Thread.currentThread() + "get o1");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "try to get o2...");
                synchronized (o2) {
                    System.out.println(Thread.currentThread() + "get o2");
                }
            }
        }, "Thread 1").start();

        new Thread(() -> {
            synchronized (o2) {
                System.out.println(Thread.currentThread() + "get o2");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "try to get o1...");
                synchronized (o1) {
                    System.out.println(Thread.currentThread() + "get o1");
                }
            }
        }, "Thread 2").start();
    }

    /**
     * 使用算法对资源分配进行评估，使其进入安全状态
     * 
     * 安全状态：
     *    系统能够按照某种线程推进顺序（P1、P2...Pn）为每个线程分配所需资源
     *    直到满足每个线程对资源的最大需求，让每个线程都可以顺利完成。
     */
    public static void notBeDeadlock() {
        new Thread(() -> {
            synchronized (o1) {
                System.out.println(Thread.currentThread() + "get o1");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "try to get o2...");
                synchronized (o2) {
                    System.out.println(Thread.currentThread() + "get o2");
                }
            }
        }, "Thread 1").start();

        new Thread(() -> {
            synchronized (o1) {
                System.out.println(Thread.currentThread() + "get o1");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "try to get o2...");
                synchronized (o2) {
                    System.out.println(Thread.currentThread() + "get o2");
                }
            }
        }, "Thread 2").start();
    }

    public static void main(String[] args) {
        System.out.println();
        notBeDeadlock();
    }
}
