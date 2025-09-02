package com.acheron.orderserver.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic ordersTopic() {
        return new NewTopic("orders", 3, (short) 1); // 3 партиції, replication factor = 1
    }
}
