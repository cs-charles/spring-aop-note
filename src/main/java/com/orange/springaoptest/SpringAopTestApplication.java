package com.orange.springaoptest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringAopTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAopTestApplication.class, args);
    }

}
