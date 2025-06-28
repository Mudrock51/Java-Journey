package org.example.concurrent.thread.lock;

/**
 * 模拟 CAS（Compare-And-Swap） 的基本实现
 */
public class SimulatedCASDemo {
    // 模拟一个共享变量 value
    private volatile static int value = -1;

    public static int getValue() {
        return value;
    }

    // 原子更新方法
    public static boolean compareAndSwap(int expectedValue, int newValue) {
        synchronized (SimulatedCASDemo.class) {
            if (value == expectedValue) {
                value = newValue;
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            int threadId = i;
            new Thread(() -> {
                int oldValue;
                boolean updated = false;
                // 自旋操作 do-while 可能会给 CPU 带来大执行开销
                do {
                    oldValue = getValue();
                    int newValue = oldValue + 1;
                    updated = compareAndSwap(oldValue, newValue);
                    System.out.println(Thread.currentThread() +
                            " : 尝试从 " + oldValue + " 更新为 " + newValue + " -> " + (updated ? "成功" : "失败"));
                    try {
                        Thread.sleep(100);
                        System.out.println();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (!updated);
            }, "ThreadId#" + threadId).start();
        }
    }
}
