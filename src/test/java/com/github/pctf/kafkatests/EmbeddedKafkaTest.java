package com.github.pctf.kafkatests;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.kafka.test.utils.KafkaTestUtils.consumerProps;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class EmbeddedKafkaTest {
    @SuppressWarnings({"unused", "RedundantSuppression"})
    protected Logger log = LoggerFactory.getLogger(getClass());
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected EmbeddedKafkaBroker embeddedKafka;

    @BeforeEach
    void assertKafkaAutowired() {
        assertNotNull(embeddedKafka);
    }

    protected <T, D> Producer<T, D> createProducer() {
        Map<String, Object> producerProps = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafka));
        return new DefaultKafkaProducerFactory<T, D>(producerProps).createProducer();
    }

    protected <T, D> Consumer<T, D> createConsumer(String topic) {
        Map<String, Object> consumerProps = consumerProps(getClass().toString(), "true", embeddedKafka);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        Consumer<T, D> consumer = new DefaultKafkaConsumerFactory<T, D>(consumerProps)
                .createConsumer();
        consumer.subscribe(Collections.singleton(topic));
        return consumer;
    }

}
