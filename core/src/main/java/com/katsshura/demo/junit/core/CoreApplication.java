package com.katsshura.demo.junit.core;

import com.katsshura.demo.junit.core.config.JpaRepositoryConfiguration;
import com.katsshura.demo.junit.core.config.PropertiesSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"com.katsshura.demo.junit"})
@Import(JpaRepositoryConfiguration.class)
@PropertiesSource
public class CoreApplication {
    public static void main(final String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}
