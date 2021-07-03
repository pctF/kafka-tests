package com.github.pctf.kafkatests.kafka.listener.impl;

import com.github.pctf.kafkatests.EmbeddedKafkaTest;
import com.github.pctf.kafkatests.kafka.listener.QueueListener;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class StringQueueListenerTest extends EmbeddedKafkaTest {

    /**
     * It is cleaner if {@link QueueListener <String>} interface were tested,
     * but it seems like usage of {@link org.mockito.Mockito#verify}(StringQueueListener, #timeout...)#receive
     * is easiest way to write such async test
     */
    @SpyBean
    private StringQueueListener stringQueueListener;
    @Value("${kafka.queue.string.topic}")
    private String listenerTopic;
    private Producer<Integer, String> producer;

    @BeforeEach
    void setUp() {
        assertNotNull(listenerTopic);
        producer = createProducer();
    }

    @AfterEach
    void tearDown() {
        producer.close();
    }

    @Test
    void receive() {
        for (int messageNumber = 1; messageNumber < 2; messageNumber++) {
            testReceiveMessage(messageNumber);
        }
    }

    private void testReceiveMessage(int messageNumber) {
        final String message = UUID.randomUUID().toString();

        producer.send(new ProducerRecord<>(listenerTopic, messageNumber, message));

        verify(stringQueueListener, timeout(1000).times(messageNumber)).receive(any());
        assertEquals(message, stringQueueListener.poll());
    }

}