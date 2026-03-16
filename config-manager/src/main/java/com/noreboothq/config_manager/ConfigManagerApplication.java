package com.noreboothq.config_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ConfigManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigManagerApplication.class, args);
	}

}
