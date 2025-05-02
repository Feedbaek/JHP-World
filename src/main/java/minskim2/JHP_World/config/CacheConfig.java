package minskim2.JHP_World.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.tmax.proobject.minskim2.cache.DualCacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(10)))
                .build();
    }

    @Bean
    public CaffeineCacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                        .initialCapacity(50)                       // 초기 용량
                        .maximumSize(1000)                         // 최대 항목 수
                        .expireAfterWrite(Duration.ofMinutes(1))   // 쓰기 후 1분 뒤 만료
        );
        return cacheManager;
    }

    @Bean
    @Primary
    public DualCacheManager dualCacheManager(CaffeineCacheManager localManager, RedisCacheManager globalManager) {
        return new DualCacheManager(localManager, globalManager);
    }
}
