package com.webtoon.coding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class WebtoonApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebtoonApplication.class, args);
    }

}
