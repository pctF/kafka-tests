package com.github.pctf.kafkatests.kafka.util.impl;

import com.github.pctf.kafkatests.EmbeddedKafkaTest;
import com.github.pctf.kafkatests.kafka.util.KafkaProducer;
import org.apache.kafka.clients.consumer.Consumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.kafka.test.utils.KafkaTestUtils.getSingleRecord;

class StringKafkaProducerTest extends EmbeddedKafkaTest {
    @Autowired
    private KafkaProducer<String> stringKafkaProducer;
    private String topic;
    private Consumer<Integer, String> consumer;

    @BeforeEach
    void setUp() {
        topic = UUID.randomUUID().toString();
        consumer = createConsumer(topic);
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    @Test
    void send() {
        final String message = UUID.randomUUID().toString();

        stringKafkaProducer.send(topic, message);

        assertEquals(message, getSingleRecord(consumer, topic).value());
    }

}