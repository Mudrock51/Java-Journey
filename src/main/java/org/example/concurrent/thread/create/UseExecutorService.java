package org.example.concurrent.thread.create;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 使用 ExecutorService 构建线程池
 */
public class UseExecutorService {
    public static void createThreadPoolByAPI() {
        // 创建一个包含 3 个线程的固定大小线程池。
        ExecutorService pool = Executors.newFixedThreadPool(3);

        // Lambda 表达式提交任务
        for (int i = 0; i < 5; i++) {
            int taskId = i;
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + ": Task " + taskId);
            });
        }

        pool.shutdown();

    }

    public static void createThreadPoolByCustom() {
        // 自定义线程池 ThreadPoolExecutor
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
            2, 
            3, 
            0, 
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );

        for (int i = 0; i < 5; i++) {
            int taskId = i;
            pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + ": Task " + taskId);
            });
        }

        pool.close();
    }

    public static void main(String[] args) {
        createThreadPoolByCustom();
    }
}
