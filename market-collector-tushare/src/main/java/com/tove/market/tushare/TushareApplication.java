package com.tove.market.tushare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author user
 */
@SpringBootApplication(scanBasePackages = {"com.tove.market.tushare"})
@MapperScan(basePackages = {"com.tove.market.tushare.dao"})
public class TushareApplication {
    public static void main(String[] args) {
        SpringApplication.run(TushareApplication.class,args);
    }
}
