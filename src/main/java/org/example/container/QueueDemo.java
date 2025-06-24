package org.example.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;

import org.example.utils.DelayedTask;

/**
 * 添加 ArrayBlockingQueue 和 LinkedBlockingQueue (JDK1.5)
 *  带有生产者消费者模式实现的并发容器
 * 
 * 添加 SynchronousQueue (JDK1.6)
 *  不存储元素的阻塞队列
 * 
 * 添加 TransferQueue (JDK1.7)
 *  支持更多操作的阻塞队列
 * 
 * 添加 DelayQueue (JDK1.8)
 *  支持延迟获取元素的阻塞队列
 */
public class QueueDemo {
    
    /**
     * ArrayBlockingQueue 是 BlockingQueue 接口的有界队列实现
     *  常用于多线程之间的数据共享，一旦创建就不能修改其容量大小；
     *  并发控制采用可重入锁 ReentrantLock，需要获取锁才能继续操作
     *  使用 add 方法时, 如果队列元素溢出时会抛出异常(add() 调用 offer())
     *      阻塞方法: put() / take()
     *      非阻塞方法: offer() / poll()
     */
    public static void createArrayBlockingQueue1() throws Exception {
        int test_capcity = 3;
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(test_capcity);

        // 添加元素 offer/add
        System.out.println(queue.offer("Hello"));
        System.out.println(queue.add("World"));
        System.out.println(queue.offer("Goodbye"));

        // 尝试向已满的队列添加元素
        System.out.println(queue.offer("Full"));
        // queue.put("Full");
        // throws exception java.lang.IllegalStateException: Queue full
        // System.out.println(queue.add("Full")); 

        // 从队列中取出元素 poll/take
        System.out.println(queue.poll());
        // take(): if null then wait for available element
        System.out.println(queue.take()); 
        System.out.println(queue.poll());

        // 尝试从空队列中拿出元素
        System.out.println(queue.poll());
    }

    /**
     * drainTo() 方法获取并清空所有元素
     */
    public static void createArrayBlockingQueue2() {
        int test_capcity = 3;
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(test_capcity);

        for (int i = 1; i <= 3; i++) {
            queue.add(i); // add/put/offer
        }

        System.out.println("queue:" + queue);

        List<Integer> list = new ArrayList<>();

        // 获取队列中的所有元素并添加在 List 中
        queue.drainTo(list);

        System.out.println("\nAfter queue.drainTo(), queue.size(): " + queue.size());
        System.out.println("\nAfter queue.drainTo(), queue.remainingCapcity(): " + queue.remainingCapacity());

        System.out.println("\nlist:" + list);
    }

    /**
     * Producer & Consumer 生产者消费者使用方法
     *  put(): 阻塞的添加操作
     *  take(): 阻塞的取出操作
     */
    public static void createProducerAndConsumer() throws Exception {
        int test_capcity = 5;
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(test_capcity);
        CountDownLatch countDownLatch = new CountDownLatch(1);

        // 生产者 Producer 线程
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    queue.put(i);
                    System.out.println("Producer Add Element:" + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 消费者 Consumer 线程
        Thread consumer = new Thread(() -> {
            try {
                int cnt = 0;
                while (true) {
                    int element = queue.take();
                    System.out.println("Consumer Get Element:" + element);
                    ++cnt;
                    if (cnt == 10) {
                        break;
                    }
                }
                countDownLatch.countDown();
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        consumer.start();

        // waiting for thread finish
        producer.join();
        consumer.join();

        countDownLatch.await();

        producer.interrupt();
        consumer.interrupt();
    }

    /** --------------------------------------------------------------
     * DelayQueue 是用于实现延时任务的延迟队列（eg: 15 分钟未支付直接取消）
     *  基于 PriorityQueue 实现的线程安全的无界队列
     *  DelayedTask 定义在 utils 目录下，定义为延迟任务(包括过期时间、任务内容)
     */
    public static void createDelayQueue1() throws Exception {
        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();

        // 添加1s、2s、3s过期的任务
        delayQueue.add(new DelayedTask(2000, () -> System.out.println("Task 2")));
        delayQueue.add(new DelayedTask(1000, () -> System.out.println("Task 1")));
        delayQueue.add(new DelayedTask(3000, () -> System.out.println("Task 3")));

        // 取出任务并执行
        while (!delayQueue.isEmpty()) {
            DelayedTask task = delayQueue.take();
            if (task != null) {
                task.execute();
            }
        }
    }

    /** ---------------------------------------------------------------
     * PriorityQueue 是基于最小堆（Min-Heap）的优先队列，实现了 Queue 接口
     *  不同于 C++ STL 的 priority_queue，Java 的优先队列是最小值优先（小顶堆）
     */
    public static void createPriorityQueue1() throws Exception {
        // 小顶堆
        PriorityQueue<Integer> priorityQueue1 = new PriorityQueue<>();
        // 大顶堆
        PriorityQueue<Integer> priorityQueue2 = new PriorityQueue<>(
                                                    Collections.reverseOrder());

        // 添加元素
        priorityQueue1.offer(2);
        priorityQueue1.offer(5);
        priorityQueue1.offer(9);

        // 查看队首元素
        System.out.println("peek(): " + priorityQueue1.peek());
        // 弹出并返回队首元素
        Integer top;
        while ((top = priorityQueue1.poll()) != null) {
            System.out.print(top + " ");
            priorityQueue2.offer(top);
        }

        System.out.println();

        while ((top = priorityQueue2.poll()) != null) {
            System.out.print(top + " ");
        }
    }
}
