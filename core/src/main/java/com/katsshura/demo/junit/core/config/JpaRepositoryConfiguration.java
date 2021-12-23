package com.katsshura.demo.junit.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.katsshura.demo.junit")
@EnableTransactionManagement
@EntityScan("com.katsshura.demo.junit.core.entities")
public @interface JpaRepositoryConfiguration {
}
