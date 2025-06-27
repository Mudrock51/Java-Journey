package org.example.concurrent.thread.create;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * ForkJoin 是 JDK1.7 引入的新线程池（基于分治的思想）
 * JDK1.8 的 parallelStream 并行流就是基于 ForkJoin 实现的
 */
public class UseForkJoinPool {

    public static void useForkJoinPool() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        for (int i = 0; i < 5; i++) {
            int taskId = i;
            forkJoinPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + ": Task " + taskId);
            });
        }

        forkJoinPool.close();
    }

    public static void useParalleStream() {
        List<String> list = Arrays.asList("Task", "was", "Done");
        list.parallelStream().forEach(System.out::println);
    }

    public static void main(String[] args) {
        // useForkJoinPool();
        useParalleStream();
    }
}
