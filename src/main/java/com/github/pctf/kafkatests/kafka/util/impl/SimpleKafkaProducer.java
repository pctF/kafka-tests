package com.github.pctf.kafkatests.kafka.util.impl;

import com.github.pctf.kafkatests.kafka.util.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class SimpleKafkaProducer<T> implements KafkaProducer<T> {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final KafkaTemplate<String, T> kafkaTemplate;

    public SimpleKafkaProducer(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topic, T payload) {
        log.debug("send() called with: topic={}, payload={}", topic, payload);
        kafkaTemplate.send(topic, payload);
    }
}
