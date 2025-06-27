# Create Thread



**① 继承 Thread 类**：

- 继承 `Thread` 类，重写 `run()`， [**<u><span style="color:#00FFFF;">ExtendsThread.java</span></u>**]

**② 实现 Runnable 接口**：

- 实现 `Runnable` 接口并重写 `run()`， [**<u><span style="color:#00FFFF;">ImplementsRunnable.java</span></u>**]

**③ 实现 Callable 接口**：

- 实现 `Callable` 接口并重写 `call()` 方法，通过 futureTask 拿到返回值，[**<u><span style="color:#00FFFF;">ImplementsCallable.java</span></u>**]

**④ 使用 ExecutorService 构建线程池或自定义 ThreadPoolExecutor 线程池**，

-  [<u>**<span style="color:#00FFFF;">UseExecutorService.java</span>**</u>]

**⑤ 使用 CompletableFuture 构建异步任务**，

-  [**<u><span style="color:#00FFFF;">UseCompletableFuture.java</span></u>**]

**⑥ 使用 ForkJoinPool/ParalleStream 构建分治线程池**，

-  [**<u><span style="color:#00FFFF;">UseForkJoinPool.java</span></u>**]

**⑦ 使用 Timer 定时器构建定时任务**，

-  [**<u><span style="color:#00FFFF;">UseTimer.java</span></u>**]