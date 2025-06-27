package org.example.concurrent.thread.create;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 实现 Callable 接口能够拿到线程执行的返回值
 */

public class ImplementsCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("\nCurrent Thread ID:" + Thread.currentThread().threadId());
        System.out.println("Start a New Thread");
        return "Done";
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Main Thread ID:" + Thread.currentThread().threadId());
        ImplementsCallable implementsCallable = new ImplementsCallable();
        FutureTask<String> futureTask = new FutureTask<>(implementsCallable);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
