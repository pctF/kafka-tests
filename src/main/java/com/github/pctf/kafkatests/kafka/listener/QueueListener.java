package com.github.pctf.kafkatests.kafka.listener;

public interface QueueListener<T> {
    T poll();
}
