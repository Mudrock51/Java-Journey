package org.example.container;

import org.junit.jupiter.api.Test;

public class QueueDemoTest {
    
    @Test
    public void testCreateArrayBlockingQueue1() throws Exception {
        QueueDemo.createArrayBlockingQueue1();
    }

    @Test
    public void testCreateArrayBlockingQueue2() throws Exception {
        QueueDemo.createArrayBlockingQueue2();
    }

    @Test
    public void testCreateProducerAndConsumer() throws Exception {
        QueueDemo.createProducerAndConsumer();
    }

    @Test
    public void testCreateDelayQueue1() throws Exception {
        QueueDemo.createDelayQueue1();
    }

    @Test
    public void testCreatePriorityQueue1() throws Exception {
        QueueDemo.createPriorityQueue1();
    }
}
