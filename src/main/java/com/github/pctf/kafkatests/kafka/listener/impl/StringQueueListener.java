package com.github.pctf.kafkatests.kafka.listener.impl;

import com.github.pctf.kafkatests.kafka.listener.QueueListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.LinkedList;

@Component
class StringQueueListener implements QueueListener<String> {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Deque<String> payloads = new LinkedList<>();

    @KafkaListener(topics = "${kafka.queue.string.topic}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        log.debug("receive() called with consumerRecord={}", consumerRecord);
        payloads.addLast(consumerRecord.value().toString());
    }


    @Override
    public String poll() {
        return payloads.poll();
    }

}
