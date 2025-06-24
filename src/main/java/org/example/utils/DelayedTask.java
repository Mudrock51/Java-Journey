package org.example.utils;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedTask implements Delayed {
        
        private long executeTime; // 任务到期时间
        private Runnable task;    // 任务

        public DelayedTask(long delay, Runnable task) {
            this.executeTime = System.currentTimeMillis() + delay;
            this.task = task;
        }

        // 查看当前任务过期时间
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(executeTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        // 延迟队列需要到期时间升序入队, 实现 compareTo 比较到期时间
        @Override
        public int compareTo(Delayed o) {
            return Long.compare(this.executeTime, ((DelayedTask) o).executeTime);
        }

        public void execute() {
            task.run();
        }
    }
