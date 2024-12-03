package com.myproject.callabo_user_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CallaboUserBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CallaboUserBootApplication.class, args);
    }

}
