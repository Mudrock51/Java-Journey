package org.example.concurrent.thread.lock;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 乐观锁 - 基于 CAS 操作的原子类
 */
public class AtomicIntegerDemo {
    // 原子类 AtomicXXXX 默认是 volatile 的
    private volatile static AtomicInteger value = new AtomicInteger(-1);

    public static void main(String[] args) throws InterruptedException {

        System.out.println();

        // 自定义线程池
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                4,
                4,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        // 1、执行 原子自增 操作
        for (int i = 0; i < 3; i++) {
            // 1.1、注册并提交任务
            pool.submit(() -> {
                int before = value.get();
                int after = value.getAndIncrement(); // 原子自增
                System.out.println(Thread.currentThread() + " : getAndIncrement 前: " + before + ", 返回值: " + after
                        + ", 当前值: " + value.get());
            });
        }

        // 2、执行原子加操作（addAndGet）
        for (int i = 1; i <= 2; i++) {
            int delta = i;
            pool.submit(() -> {
                int newVal = value.addAndGet(delta); // 原子加并返回结果
                System.out.println(
                        Thread.currentThread() + " : addAndGet(" + delta + ") -> 当前值: " + newVal);
            });
        }

        // 3、执行 CAS 操作（compareAndSet）
        pool.submit(() -> {
            int expect = value.get();
            boolean result = value.compareAndSet(expect, 100);
            System.out.println(
                    Thread.currentThread() + " : compareAndSet(" + expect + ", 100) -> " + result + ", 当前值: "
                            + value.get());
        });

        // 4、执行设置操作（set）
        pool.submit(() -> {
            value.set(888);
            System.out.println(Thread.currentThread() + " : set(888), 当前值: " + value.get());
        });

        // 等待所有任务执行完
        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("主线程最终值: " + value.get());
    }
}
