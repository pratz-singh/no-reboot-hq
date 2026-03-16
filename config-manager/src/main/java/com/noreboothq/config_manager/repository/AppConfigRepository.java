package com.noreboothq.config_manager.repository;

import com.noreboothq.config_manager.entity.AppConfig; // <-- Notice the underscore here!
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppConfigRepository extends JpaRepository<AppConfig, Long> {
    
    // Spring Boot automatically translates this method name into a SQL query!
    Optional<AppConfig> findTopByConfigKeyOrderByVersionDesc(String configKey);
}