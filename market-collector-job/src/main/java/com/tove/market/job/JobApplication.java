package com.tove.market.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author loorr
 */
@ComponentScan("com.tove.market")
@MapperScan(basePackages = {"com.tove.market.dao.major", "com.tove.market.dao.temp"})
@SpringBootApplication(scanBasePackages = {"com.tove.market","com.tove.market.dao.major"})
public class JobApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }

}