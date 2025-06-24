package org.example.container;

import java.util.HashSet;
import java.util.TreeSet;

/**
 * Set 包括 HashSet、LinkedHashSet 和 TreeSet
 */
public class SetDemo {
    
    /**
     * HashSet 底层数据结构是基于 HashMap 实现的哈希表
     * - 不需要保证元素的插入和取出顺序的场景
     */
    public static void createHashSet1() {
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(5);
        hashSet.add(10);
        hashSet.add(50);
        hashSet.add(1);
        hashSet.add(2);

        for (Integer i : hashSet) {
            System.out.println(i); // [1,50,2,5,10]
        }
    }

    /**
     * TreeSet 底层的数据结构是红黑树, 保证元素有序
     * - 常用于支持对元素自定义排序规则的场景
     */
    public static void createTreeSet1() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(5);
        treeSet.add(10);
        treeSet.add(50);
        treeSet.add(1);
        treeSet.add(2);

        for (Integer i : treeSet) {
            System.out.println(i); // [1,2,5,10,50]
        }
    }
}
