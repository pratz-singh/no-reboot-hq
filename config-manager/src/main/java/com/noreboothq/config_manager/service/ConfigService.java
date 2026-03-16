package com.noreboothq.config_manager.service;

import com.noreboothq.config_manager.entity.AppConfig;
import com.noreboothq.config_manager.repository.AppConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigService {

    @Autowired
    private AppConfigRepository repository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String KAFKA_TOPIC = "config-updates";

    // 1. READ: If it's in Redis ("configs"), return it instantly. If not, check Postgres and save it to Redis!
    @Cacheable(value = "configs", key = "#key")
    public Optional<AppConfig> getConfig(String key) {
        return repository.findTopByConfigKeyOrderByVersionDesc(key);
    }

    // 2. WRITE: @CacheEvict rips up the old sticky note in Redis so we don't accidentally serve stale data.
    @CacheEvict(value = "configs", key = "#key")
    public AppConfig updateConfig(String key, String value) {
        int newVersion = 1;
        Optional<AppConfig> existingConfig = repository.findTopByConfigKeyOrderByVersionDesc(key);

        if (existingConfig.isPresent()) {
            newVersion = existingConfig.get().getVersion() + 1;
        }

        AppConfig newConfig = new AppConfig();
        newConfig.setConfigKey(key);
        newConfig.setConfigValue(value);
        newConfig.setVersion(newVersion);

        AppConfig savedConfig = repository.save(newConfig);

        String message = String.format("{\"key\": \"%s\", \"value\": \"%s\", \"version\": %d}", key, value, newVersion);
        kafkaTemplate.send(KAFKA_TOPIC, key, message);

        return savedConfig;
    }
}