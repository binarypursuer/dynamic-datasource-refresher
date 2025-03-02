package com.github.binarypursuer.refresher.example.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动类
 *
 * @author binarypursuer
 * @version 1.0
 * @date 2025/3/2
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NaocsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NaocsApplication.class, args);
    }
}