# 锁 🔒 Lock

## 悲观锁

> [!tip]
>
> **<span style="font-size:1.4em; color:#0080FF;">悲观锁</span>**：每次<span style="color:#FF0000;">只能有一个线程使用</span>，其它线程会 **阻塞**。
>
> - 总是假设最坏情况，认为共享资源每次被访问的时候都 **<u>会被修改</u>**
> - 每次获取资源操作都会上锁
> - 如果无法获取锁，就会阻塞到上一个持有者释放



Java 的 `synchronized` 和 `ReentrantLock` 等独占锁。

- `synchronized` 依赖于<span style="color:#FF0000;"> JVM 实现</span>的，没有暴露接口。

- ReentrantLock 是 <span style="color:#FF0000;">JDK 层面（API 层面）实现</span>的，需要调用 `lock()` 和 `unlock()` 方法配合 `try/finally` 语句完成。

---

### `synchronized`

`synchronized` 关键字使用方式有 3 种：

- 修饰实例方法（锁当前对象实例）：`synchronized void method() {}`
- 修饰静态方法（锁当前类）：`synchronized static void method() {}`
- 修饰代码块（锁指定对象/类）：`synchronized (this) {}`

<span style="color:#00FF00;">`static` 修饰的静态 synchronized 方法和  非静态 synchronized 方法 的调用不互斥。</span>



####  synchronized 底层原理

synchronized <span style="color:#FF0000;">同步语句块</span>的实现是使用 `monitorenter` 和 `monitorexit` 指令。

- `monitorenter` 指令指向同步代码块的开始位置；
- `monitorexit` 指令指向同步代码块结束的位置。



`synchronized` <span style="color:#FF0000;">修饰的方法</span>通过 `ACC_SYNCHRONIZED` 标识指明该方法是一个同步方法。



#### synchronized 废弃偏向锁

> 偏向锁是 HotSpot 虚拟机的一项优化技术，提升单线程对同步代码块的访问性能，常用于早期的 Java 集合 API（HashTable、Vector）。



`ConcurrentHashMap` 高性能集合在内部实现了性能优化，偏向锁的性能收益降低（不明显）。同时，多线程竞争需要 <u><span style="color:#808080;">撤销偏向锁</span></u>，这个操作的性能开销很昂贵（需要等待进入全局安全点(Safe Point)），该状态下所有线程都是暂停的。

- JVM 内部代码维护成本太高





#### synchronized 与 volatile

|                         synchronized                         |                           volatile                           |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| <span style="color:#FF0000;">可以修饰变量、方法和代码块</span> |       <span style="color:#0080FF;">只能修饰变量</span>       |
|                   保证数据的可见性、原子性                   | 保证数据的可见性，<span style="color:#0080FF;">不保证原子性</span> |
| 关注多个线程之间的资源<span style="color:#FF0000;">同步性</span> | 解决多个线程之间的资源<span style="color:#0080FF;">可见性</span> |



### `ReentrantLock`

`ReentrantLock` 实现了 `Lock` 接口，是一个可重入且独占式的锁。

相比于 `synchronized`，`ReentrantLock` 还包括：

- 轮询：借助 <span style="color:#FF0000;">Condition 和 newCondition()</span> 方法实现选择性通知；
- 超时：提供 `tryLock(timeout)` 方法，<span style="color:#0080FF;">指定获取锁的最长等待时间</span>；
- 中断：`lock.lockInterruptibly();` 
- 公平锁：先申请的线程先获取被释放的锁（性能较差）
- 非公平锁：后申请的线程 <span style="color:#0080FF;">可能</span> 先得到被释放的锁（某些线程可能无法获取锁）

```java
public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}
```



#### 线程中断

线程中断 `interrupt()` 将线程的<span style="color:#0080FF;">中断状态标志</span>设置为 true。

➤ 如 Object.wait()、Thread.sleep()、lock.lockInterruptibly() 在中断标志为 true 时会抛出 InterruptedException。

➤ 抛出 InterruptedException 异常后线程 **不会自动退出**，线程会自动跳转到 `catch` 代码块执行后续代码。

> [!important]
> 中断不是强行杀死线程，而是一个“温柔的提醒“。





## 乐观锁

>  [!tip]
>
> <span style="font-size:1.4em;">**<span style="color:#0080FF;">乐观锁</span>**</span>：
>
> - 总是假设最好情况，认为共享资源每次被访问时都 **<u>不会被修改</u>**
> - 获取资源无需加锁，无需等待
> - 提交时验证（版本号机制、<span style="font-family:Georgia;">**CAS**</span> 算法）



### 乐观锁

原子变量类：`java.util.concurrent.atomic` 包的原子变量类。

➤ AtomicInteger

➤ LongAddr

使用了乐观锁的一种实现方式 **<span style="font-family:Georgia;">CAS</span>** 实现的。



> [!tip]
>
> `LongAddr` 中高并发场景下通过 <span style="color:#FF0000;">空间换时间</span> 的代价比 `AtomicInteger` 和 `AtomicLong` 性能更好。



### 版本号机制

在数据表中加入 `version` 版本字段，表示<span style="color:#FF0000;">数据被修改的次数</span>。

- 当数据被修改时，`version++`（自增）；
- 线程 A 尝试更新数据值时，在提交更新时，只有当读取到的 `version` 值与当前数据库中的 `version` 值相等时才更新，否则重试更新操作，直到成功。



### <span style="font-family:Georgia;">CAS</span> 算法

> **<span style="font-family:Georgia;">CAS</span>** 全称 <span style="font-family:Georgia;">**Compare And Swap**</span>（比较与交换），用于实现乐观锁。

<span style="font-family:Georgia;">CAS</span> 是一个原子操作，底层依赖于一条 <span style="font-family:Georgia;">CPU</span> 的<span style="color:#FF0000;">原子操作</span>。

- ***原子操作* *：最小不可拆分的操作，一旦开始就不能被打断，直到操作完成。*



<span style="font-family:Georgia;">CAS</span> 涉及到三个操作数

➤ <span style="font-family:Georgia;">**V**</span>：要更新的变量值（Variable）

➤ <span style="font-family:Georgia;">**E**</span>：预期值（Expected）

➤ **<span style="font-family:Georgia;">N</span>**：拟写入的新值（New）



多个线程同时使用 <span style="font-family:Georgia;">**CAS**</span> 操作一个变量时，只有一个线程会胜出并成功更新变量。其余的线程均会失败而且不会挂起，仅被告知失败。

Java 的 `sun.misc` 包下的 `Unsafe` 类提供了 `compareAndSwapObject`、`compareAndSwapInt`、`compareAndSwapLong` 方法来实现对应类型的 <span style="font-family:Georgia;">**CAS**</span> 操作。



#### ABA 问题

> <span style="font-family:Georgia;">CAS</span> 操作会在第一次读取和赋值时检测变量值，但是却并不能保证期间是否有修改，即 <span style="font-family:Georgia;">**ABA**</span> 问题。