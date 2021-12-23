package com.katsshura.demo.junit.core.config;

import org.springframework.context.annotation.PropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@PropertySource("classpath:application.properties")
@PropertySource("classpath:core.properties")
@PropertySource("classpath:core-${spring.profiles.active}.properties")
public @interface PropertiesSource {
}
