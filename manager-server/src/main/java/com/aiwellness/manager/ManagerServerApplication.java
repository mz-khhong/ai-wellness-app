package com.aiwellness.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.aiwellness")
public class ManagerServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerServerApplication.class, args);
    }
}

