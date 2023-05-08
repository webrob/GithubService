package com.empik.githubservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GithubServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubServiceApplication.class, args);
    }

}
