package org.example.concurrent.thread.create;

import java.util.concurrent.CompletableFuture;

// 异步任务: 直接返回而不等待任务结果，当任务结束时发出提醒
public class UseCompletableFuture {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("\nCurrent Thread ID:" + Thread.currentThread().threadId());
                    System.out.println("Start a New Async Task");
                    return "Done";
                });

        // 等待任务完成
        String result = completableFuture.join();
        System.out.println("Async Task Done: " + result);
    }
}
