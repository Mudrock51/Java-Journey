package org.example.concurrent.thread.create;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 使用定时器类 Timer 构建定时任务（非多线程）
 */
public class UseTimer {
    public static void main(String[] args) {
        Timer timer = new Timer();

        for (int i = 0; i < 5; i++) {
            int taskId = i;
            /**
             * 执行定时任务
             * timer.schedule(task, 启动后多久开始执行, 每间隔多久执行一次)
             */
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ": Execute task " + taskId);
                }
            }, 0, 1000);
        }
    }
}
