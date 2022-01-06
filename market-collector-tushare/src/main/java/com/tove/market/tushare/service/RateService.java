package com.tove.market.tushare.service;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author user
 */
@Service
public class RateService {

    private RateLimiter rateLimiter;

    private RateService(){
        rateLimiter = RateLimiter.create(499/60);
    }

    public boolean tryAcquire(){
        return rateLimiter.tryAcquire(1, 10, TimeUnit.SECONDS);
    }

}
