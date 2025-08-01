package org.acheron.kafka2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "yo", groupId = "my-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
