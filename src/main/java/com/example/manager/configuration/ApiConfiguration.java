package com.example.manager.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "api")
@Data
public class ApiConfiguration {
	private String basePath;
}
