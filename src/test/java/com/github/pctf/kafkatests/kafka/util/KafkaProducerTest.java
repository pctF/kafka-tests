package com.github.pctf.kafkatests.kafka.util;

import com.github.pctf.kafkatests.kafka.listener.QueueListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class KafkaProducerTest {

    @Autowired
    private KafkaProducer<String> stringKafkaProducer;
    @Autowired
    private QueueListener<String> queueListener;
    @Value("${kafka.queue.string.topic}")
    private String topic;

    @Test
    void send() throws InterruptedException {
        assertNotNull(topic);
        final String testPayload = "testPayload";

        stringKafkaProducer.send(topic, testPayload);
        Thread.sleep(1000);

        assertEquals(testPayload, queueListener.poll());

        stringKafkaProducer.send(topic, testPayload + testPayload);
        Thread.sleep(1000);
        assertEquals(testPayload + testPayload, queueListener.poll());
    }
}