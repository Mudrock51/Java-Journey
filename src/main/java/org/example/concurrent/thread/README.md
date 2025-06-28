# Java 并发(Concurrent)

## volatile

`volatile` 关键字：

- 保证变量的 **可见性**（将变量注册到主存，共享给所有线程）
- 防止 JVM 的指令重排序。



> [!note]
>
> <span style="color:#0080FF;">**<u>指令重排序</u>**</span>
>
> - 编译器和处理器为了提高程序执行效率的优化手段，在不改变单线程语义的前提下，改变指令的实际执行顺序。
> - 在 **多线程程序** 中，指令重排序可能会引发严重问题 —— 线程之间共享变量的访问顺序可能与代码写的顺序不一致，从而导致 **线程安全问题** 或 **不可预期的结果**。



**防止指令重排序的方法**

|      方法       |                             说明                             |
| :-------------: | :----------------------------------------------------------: |
|   `volatile`    | Java 关键字，禁止 🚫 读写操作的重排序。写 `volatile` 前会插入 StoreFence 屏障；读 `volatile` 前会插入 LoadFence 屏障 |
| `synchronized`  | Java 的内置锁机制，进入和退出同步块时会插入内存屏障，防止指令重排。 |
| `Atomic` 原子类 |              底层 `Unsafe` 实现，包含内存屏障。              |



**指令重排序的危害**

```java
public class Singleton {
    // 单例模式唯一实例
    private volatile static Singleton uniqueInstance; 
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
```



`new Singleton()` 的运行逻辑应该是 `1->2->3`，但是由于指令重排序，可能导致执行顺序变成：`1->3->2`，使得在其它线程判断的过程中 `uniqueInstance` 对象仍然为 `null`。



