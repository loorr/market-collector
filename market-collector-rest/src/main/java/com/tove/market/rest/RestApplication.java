package com.tove.market.rest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author loorr
 */

@EnableSwagger2
@EnableDiscoveryClient
@ComponentScan("com.tove.market")
@MapperScan("com.tove.market.dao.major")
@EnableFeignClients(value = {"com.tove.market.api"})
@SpringBootApplication(scanBasePackages = {"com.tove.market","com.tove.market.dao.major"})
public class RestApplication {
    public static void main(String[] args) {
        System.setProperty("eureka.instance.ip-address","1.1.1.1");
        SpringApplication.run(RestApplication.class,args);
    }
}
