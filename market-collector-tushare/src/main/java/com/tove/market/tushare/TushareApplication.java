package com.tove.market.tushare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author user
 */
@SpringBootApplication(scanBasePackages = {"com.tove.market.tushare"})
public class TushareApplication {
    public static void main(String[] args) {
        SpringApplication.run(TushareApplication.class,args);
    }
}
