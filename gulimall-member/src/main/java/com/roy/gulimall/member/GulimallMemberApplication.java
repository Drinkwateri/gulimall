package com.roy.gulimall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 想要远程调用别的服务？
 *      1.引入openfeign
 *      2.编写一个接口，告诉Spring Cloud这个接口需要调用远程服务
 *          声明接口的每个方法都是调用哪个远程服务的哪个请求
 *      3.开启远程调用功能
 *          @EnableFeignClients(basePackages = "com.roy.gulimall.member.feign")
 *
 */
@EnableFeignClients(basePackages = "com.roy.gulimall.member.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallMemberApplication.class, args);
    }

}
