package org.example.container;

import org.junit.jupiter.api.Test;

public class HashMapDemoTest {
    
    @Test
    public void testCreateHashMap1() {
        HashMapDemo.createHashMap1();
    }

    @Test
    public void testUnsafeHashMap1() throws InterruptedException {
        HashMapDemo.unsafeHashMap1();
    }

    @Test
    public void testConcurrentHashMap1() throws InterruptedException {
        HashMapDemo.createConcurrentHashMap1();
    }
}
