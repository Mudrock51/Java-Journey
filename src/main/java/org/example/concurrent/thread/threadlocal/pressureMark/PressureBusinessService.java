package org.example.concurrent.thread.threadlocal.pressureMark;

/**
 * 模拟压力测试业务类
 */
public class PressureBusinessService {
    public void handleRequest() {
        if (PressureContext.isPressure()) {
            System.out.println("🔥 处理压测流量");
        } else {
            System.out.println("✅ 处理真实流量");
        }
    }
}
