package com.lezhin.coding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LezhinCodingApplication {

    public static void main(String[] args) {
        SpringApplication.run(LezhinCodingApplication.class, args);
    }

}
