package br.com.mediapro.test.userserver.config;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@CacheConfig(cacheNames={"users", "download"})
public class CachingConfig {
}
