package org.example.concurrent.thread.threadlocal.pressureMark;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 压力标志类，使用 TransmittableThreadLocal 作为线程池共用的 ThreadLocal
 */
public class PressureContext  {
    // 1、TransmittableThreadLocal 实例(TTL)
    private static final TransmittableThreadLocal<Boolean> pressureMark = new TransmittableThreadLocal<>();

    // 2、配置压力标志
    public static void setPressureMark(Boolean isPressure) {
        pressureMark.set(isPressure);
    }

    // 3、压力标志判断
    public static Boolean isPressure() {
        return pressureMark.get() != null && pressureMark.get();
    }

    // 4、清理 ThreadLocal
    public static void clear() {
        pressureMark.remove();
    }
}
