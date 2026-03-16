package com.noreboothq.config_manager.controller;

import com.noreboothq.config_manager.entity.AppConfig;
import com.noreboothq.config_manager.repository.AppConfigRepository;
import com.noreboothq.config_manager.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/configs")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @Autowired
    private AppConfigRepository repository;

    // 1. GET Endpoint: Allows clients to fetch the latest configuration via an API
    @GetMapping("/{key}")
    public ResponseEntity<AppConfig> getConfig(@PathVariable String key) {
        Optional<AppConfig> config = configService.getConfig(key);
        
        // If the config exists, return it with a 200 OK. Otherwise, return a 404 Not Found.
        return config.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 2. POST Endpoint: Allows authorized systems to create or update a configuration
    @PostMapping
    public ResponseEntity<AppConfig> updateConfig(@RequestParam String key, @RequestParam String value) {
        // This calls the "brain" of our app to handle the versioning and Kafka broadcast
        AppConfig updatedConfig = configService.updateConfig(key, value);
        return ResponseEntity.ok(updatedConfig);
    }
}
