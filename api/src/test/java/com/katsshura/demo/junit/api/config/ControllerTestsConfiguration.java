package com.katsshura.demo.junit.api.config;

import com.katsshura.demo.junit.api.ApiApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SpringBootTest(classes = ApiApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ControllerTestsConfiguration {
}
