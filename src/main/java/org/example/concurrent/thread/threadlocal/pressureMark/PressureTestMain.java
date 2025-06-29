package org.example.concurrent.thread.threadlocal.pressureMark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.ttl.threadpool.TtlExecutors;

/**
 * 主程序测试类，模拟线程池传递上下文
 */
public class PressureTestMain {
    public static void main(String[] args) {
        // 1、TTL 包装的线程池
        ExecutorService executor = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(2));

        // 2、主线程设置压测标记 pressureMark
        PressureContext.setPressureMark(true);

        // 3、提交任务
        executor.submit(() -> {
            new PressureBusinessService().handleRequest();
        });

        // 4、清除上下文（防止 ThreadLocal 泄露）
        PressureContext.clear();
        executor.shutdown();
    }
}
