package com.github.pctf.kafkatests.configudation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;


public class KafkaConfiguration {

    @Bean
    ConcurrentKafkaListenerContainerFactory<Integer, String>
    kafkaListenerContainerFactory(ConsumerFactory<Integer, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory(@Qualifier("kafkaConsumerConfig") Map<String, Object> consumerConfig) {
        return new DefaultKafkaConsumerFactory<>(consumerConfig);
    }

    @Bean("kafkaConsumerConfig")
    @ConfigurationProperties(prefix = "kafka.consumer.properties")
    public Map<String, Object> consumerConfig() {
        return new HashMap<>();
    }

    @Bean
    public ProducerFactory<Integer, String> producerFactory(@Qualifier("kafkaProducerConfig") Map<String, Object> producerConfig) {
        return new DefaultKafkaProducerFactory<>(producerConfig);
    }

    @Bean("kafkaProducerConfig")
    @ConfigurationProperties(prefix = "kafka.producer.properties")
    public Map<String, Object> producerConfig() {
        return new HashMap<>();
    }

    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate(ProducerFactory<Integer, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
