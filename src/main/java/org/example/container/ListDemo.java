package org.example.container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Array、ArrayList、CopyOnWriteArrayList
 * https://javaguide.cn/java/collection/java-collection-questions-01.html
 */
public class ListDemo {

    /**
     * Array 定长数组使用示例
     */
    public static void createArray1() {
        String[] strings = new String[]{"hello", "world", "!"};

        strings[0] = "goodbye";
        System.out.println(Arrays.toString(strings)); // [goodbye, world, !]

        for (int i = 0; i < strings.length - 1; i++) {
            strings[i] = strings[i + 1];
        }
        strings[strings.length-1] = null;
        System.out.println(Arrays.toString(strings));
    }

    /**
     * ArrayList 变长数组使用示例
     */
    public static void createArrayList1() {
        // 动态数组
        List<String> list = new ArrayList<>(Arrays.asList("hello", "world", "!"));
        // 添加元素到 list
        list.add("goodbye");
        System.out.println(list); // [hello, world, !, goodbye]

        // 修改 list 元素
        list.set(0, "hi");
        System.out.println(list); // [hi, world, !, goodbye]
        
        // 删除 list 中的元素
        list.remove(0);
        System.out.println(list); // [world, !, goodbye]
    }

    /**
     * CopyOnWriteArrayList 并发安全变长数组使用示例
     */
    public static void createThreadSafeList1() {
        // 线程安全的 CopyOnWriteArrayList 避免异常 “ConcurrentModificationException”
        // List<Integer> list = new ArrayList<>();
        List<Integer> list = new CopyOnWriteArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(2);

        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        Thread t1 = new Thread(() -> {
            // for-each 在并发条件下会出发「modCount」检查机制
            // ArrayList 在并发时会 modCount 从而抛出 ConcurrentModificationException
            for (Integer i : list) {
                try {
                    Thread.sleep(1); // sleep() 保证线程2一定能执行
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++; // Integer 不可变, 这里不会修改 list 的值
            }
            countDownLatch.countDown();
        });

        Thread t2 = new Thread(() -> {
            System.out.println("remove Integer '50'");
            list.remove(Integer.valueOf(50));
            countDownLatch.countDown();
        });

        t1.start();
        t2.start();
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
