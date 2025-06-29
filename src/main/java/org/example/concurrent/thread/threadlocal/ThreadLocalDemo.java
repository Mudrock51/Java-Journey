package org.example.concurrent.thread.threadlocal;

public class ThreadLocalDemo {

    public void testThreadLocal() {
        // 设置 ThreadLocal 默认值
        ThreadLocal<String> mThreadLocal = new ThreadLocal<String>() {
            @Override
            protected String initialValue() {
                // 设置默认值为线程的初始名称
                return Thread.currentThread().getName();
            }
        };

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                String threadName = mThreadLocal.get();
                System.out.println(Thread.currentThread() + ": [" + threadName + "]");
            }).start();
        }
    }

    public static void main(String[] args) {
        System.out.println();

        /**
         * 可以理解为身份铭牌, 一开始大家在主线程中都是 "某公司员工"，
         * 当 “线程“ 来了，就进入具体的部门，获取的是自己的身份铭牌
         * 自己的铭牌只能自己修改并使用，别的人无法修改或使用
         */
        ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
        threadLocalDemo.testThreadLocal();
    }
}
