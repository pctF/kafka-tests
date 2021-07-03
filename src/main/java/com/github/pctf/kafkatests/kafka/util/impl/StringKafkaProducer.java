package com.github.pctf.kafkatests.kafka.util.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class StringKafkaProducer extends SimpleKafkaProducer<String> {
    public StringKafkaProducer(KafkaTemplate<Integer, String> kafkaTemplate) {
        super(kafkaTemplate);
    }
}
