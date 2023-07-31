package com.cuan.gamesexplorer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import static javax.management.timer.Timer.*;

@Configuration
@EnableCaching
public class CachingConfig {

    private final Logger log = LoggerFactory.getLogger(CachingConfig.class);
    private static final String CACHE_NAME = "games";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CACHE_NAME);
    }

    @Scheduled(fixedRate = ONE_HOUR)
    @CacheEvict(value = {CACHE_NAME}, allEntries = true)
    public void clearCache() {
        log.debug("Cache '{}' cleared.", CACHE_NAME);
    }
}