package com.tove.market.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author loorr
 */
@EnableSwagger2
@EnableFeignClients(value = {"com.tove.web.market.api"})
@EnableDiscoveryClient
@SpringBootApplication(
        scanBasePackages = {"com.tove.web.market"},
        exclude = DataSourceAutoConfiguration.class
)
public class RestApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class,args);
    }
}
