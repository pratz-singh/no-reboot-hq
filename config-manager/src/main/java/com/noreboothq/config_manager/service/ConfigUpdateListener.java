package com.noreboothq.config_manager.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConfigUpdateListener {

    // This annotation tells Spring Boot to constantly monitor the Kafka topic.
    // The "groupId" is just a label for the cluster of servers listening.
    @KafkaListener(topics = "config-updates", groupId = "noreboot-group")
    public void consumeConfigUpdate(String message) {
        
        // In a real app, you would write code here to update internal memory.
        // For now, we are going to print a massive alert so you can see it working!
        System.out.println("\n=======================================================");
        System.out.println("🔥 REAL-TIME EVENT RECEIVED FROM KAFKA! 🔥");
        System.out.println("Payload: " + message);
        System.out.println("=======================================================\n");
    }
}
