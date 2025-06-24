package org.example.container;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * HashMap 是基于哈希表的 Map 接口实现的非线程安全的容器。
 * 
 * 可以存储 null 的键值对, null 作为 key 只能有一个，作为 value 可以有多个
 * JDK1.8 后, 当 HashMap 链表长度大于阈值时，就会将链表转换为红黑树，减少搜索时间
 * HashMap 总是以 2 的幂次作为哈希表的大小
 */
public class HashMapDemo {
    
    // HashMap 常用方法
    public static void createHashMap1() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Zhang San", 1);
        hashMap.put("Li Si", 2);
        hashMap.put("Zhao Wu", 3);
        hashMap.put("Wang Ermazi", 4);

        // 直接输出 HashMap
        System.out.println(hashMap);

        /**
         * 枚举遍历 HashMap
         */
        // 1.获取 HashMap 的所有键
        System.out.println("----for-each print all keys of hashMap----");
        Set<String> keys1 = hashMap.keySet();
        for (String key : keys1) {
            System.out.print(key + " ");
        }
        System.out.println();

        // 2.获取 HashMap 的所有值
        System.out.println("----for-each print all values of hashMap----");
        Collection<Integer> values = hashMap.values();
        for (Integer value : values) {
            System.out.print(value + " ");
        }
        System.out.println();

        // 3.枚举 HashMap 的 key, 同时打印对应的 value
        // O(2 * n)
        System.out.println("----print value when use for-each to print key----");
        Set<String> keys2 = hashMap.keySet();
        for (String key : keys2) {
            System.out.print(key + ":" + hashMap.get(key) + " ");
        }

        // 4.在 O(n) 的时间内一次获取 key:value
        Set<Entry<String, Integer>> entries = hashMap.entrySet();
        for (Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        /**
         * HashMap 常用方法
         */
        // hashMap.size(): 获取哈希表大小
        System.out.println("hashMap.size():" + hashMap.size());
        // hashMap.isEmpty(): 判断哈希表是否为空
        System.out.println("hashMap.isEmpty():" + hashMap.isEmpty());
        // hashMap.remove(key): 哈希表移除元素
        System.out.println(hashMap.remove("Li Si"));
        // hashMap.get(key): 根据键获取哈希表值
        System.out.println("hashMap.get(\"Zhang San\")" + hashMap.get("Zhang San"));
        // hashMap.containKey(key): 判断是否存在某个键
        System.out.println("hashMap.containKey(\"Zhao Wu\")" + hashMap.containsKey("Zhao Wu"));
        // hashMap.containValue(value): 判断是否存在某个值
        System.out.println("hashMap.containValue(4)" + hashMap.containsValue(4));
        // hashMap.replace(key, value): 替换某个键值对的值
        System.out.println("hashMap.replace(key, value)" + hashMap.replace("Zhang San", 5));
        System.out.println(hashMap);
    }

    // 设计一个 HashMap 不能满足的并发场景: 
    // 并发修改异常: ConcurrentModificationException
    public static void unsafeHashMap1() throws InterruptedException {
        HashMap<String, Integer> map = new HashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(2);

        for (int i = 0; i < 100; i++) {
            map.put(String.valueOf(i), i);
        }

        Thread t1 = new Thread(() -> {
            // (Java 10+) 类型推断 var -> Set<Entry<String, Integer>>
            var entries = map.entrySet();
            for (var entry : entries) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(entry);
            }
            countDownLatch.countDown();
        });

        Thread t2 = new Thread(() -> {
            map.remove("4");
            countDownLatch.countDown();
        });

        t1.start();
        t2.start();
        countDownLatch.await();
    }

    /**
     * 支持并发修改的哈希表 - ConcurrentHashMap
     *  */ 
    public static void createConcurrentHashMap1() throws InterruptedException {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        int threadCount = 10;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    map.put(threadId * 1000 + j, j);
                }
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        System.out.println("Final Map Size:" + map.size());
    }
}
