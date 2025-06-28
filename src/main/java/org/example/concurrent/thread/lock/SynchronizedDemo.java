package org.example.concurrent.thread.lock;

public class SynchronizedDemo {
    public void method() {
        synchronized(this) {
            System.out.println("Synchronized Code");
        }
    }
}
