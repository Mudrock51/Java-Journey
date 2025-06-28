package org.example.concurrent.thread.lock;

/**
 * 介绍 Java 的关键字: volatile
 * 
 * volatile 保证变量的可见性（将变量注册到「主存」中, 所有线程共享）
 * 
 * !!!
 * volatile 不能保证数据的原子性
 */
public class Singleton {
    private volatile static Singleton uniqueInstance; // 单例模式唯一实例
    private Singleton() {}

    // 双重检验锁实现单例模式
    public static Singleton getUniqueInstance() {
        if (uniqueInstance == null) {
            // 类对象锁
            synchronized(Singleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                    // 1. 为 instance 分配内存空间
                    // 2. 初始化 instance
                    // 3. 将 instance 指向分配的内存空间地址
                }
            }
        }
        return uniqueInstance;
    }
}
