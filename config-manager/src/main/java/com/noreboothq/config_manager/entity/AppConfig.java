package com.noreboothq.config_manager.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_configs")
@Data // This Lombok annotation automatically creates getters, setters, and constructors!
public class AppConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The name of the setting (e.g., "MAX_API_LIMIT")
    @Column(name = "config_key", nullable = false)
    private String configKey;

    // The value of the setting (e.g., "1000")
    @Column(name = "config_value", nullable = false)
    private String configValue;

    // We track versions so we can rollback if a bad config breaks the system
    @Column(nullable = false)
    private Integer version = 1;

    // Keeps track of when this specific version was created
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
