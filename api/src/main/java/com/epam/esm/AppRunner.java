package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Class {@code AppRunner} contains method to run Spring Boot application.
 *
 * @author Dmitry Poliukov
 */
@SpringBootApplication
public class AppRunner extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);

    }
}
