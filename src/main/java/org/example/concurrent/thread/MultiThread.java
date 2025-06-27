package org.example.concurrent.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * MultiThread 查看当一个 java 进程启动时有哪些线程会同时启动
 */
public class MultiThread {
    public static void main(String[] args) {
        // java 线程管理 Bean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(
                "[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName()
            );
        }
        // [3]main                                 //  main 线程，程序入口
        // [15]Reference Handler                   //  清除 reference 线程
        // [16]Finalizer                           //  调用 Finalizer 方法的线程
        // [17]Signal Dispatcher                   //  JVM 信号分发处理线程
        // [18]Attach Listener                     //  添加事件线程
        // [33]Notification Thread                 //  通知线程
        // [34]Common-Cleaner                      //  通用清理线程
    }
}
