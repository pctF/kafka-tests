package com.github.pctf.kafkatests.kafka.util;

public interface KafkaProducer<T> {
    void send(String topic, T payload);
}
