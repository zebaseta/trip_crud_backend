package com.otravo.trips.config.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    @Value("${app.config.cacheTimeout}")
    private long cacheTimeout;
    @Value("${app.config.cacheUnit}")
    private String timeUnit;
    @Value("${app.config.cacheMaxSize}")
    private long cacheMaximunSize;

    @Bean
    public CacheManager cacheManager() {
        MapTimeoutCacheManager cacheManager = new MapTimeoutCacheManager();
        cacheManager.setCacheTimeout(cacheTimeout);
        cacheManager.setTimeUnit(TimeUnit.valueOf(timeUnit));
        cacheManager.setMaximumSize(cacheMaximunSize);
        return cacheManager;
    }
}