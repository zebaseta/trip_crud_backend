package com.otravo.trips.config.cache;

import com.google.common.cache.CacheBuilder;
import lombok.Data;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Data
public class MapTimeoutCacheManager extends ConcurrentMapCacheManager {
    private Long cacheTimeout;
    private Long maximumSize;
    private TimeUnit timeUnit;

    @Override
    protected Cache createConcurrentMapCache(final String name) {
        ConcurrentMap map = CacheBuilder.newBuilder()
                .expireAfterWrite(cacheTimeout, timeUnit)
                .maximumSize(maximumSize).build().asMap();
        return new ConcurrentMapCache(name, map, false);
    }
}