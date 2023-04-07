package com.example.miniProj2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MiniProj2Application {

    public static void main(String[] args) {
        SpringApplication.run(MiniProj2Application.class, args);
    }

}
