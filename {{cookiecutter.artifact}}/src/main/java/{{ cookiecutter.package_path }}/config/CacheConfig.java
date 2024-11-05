package {{ cookiecutter.basePackage }}.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

/**
 * 缓存配置
 */
@Configuration
@EnableCaching // 开启缓存支持
public class CacheConfig {

    /**
     * 配置缓存管理器
     *
     * @return 缓存管理器
     */
    @Primary
    @Bean("caffeineCacheManager")
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                // .removalListener((key, value, cause) -> System.out.println("remove key:" + key + " value:" + value + " cause:" + cause))
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterAccess(5, TimeUnit.MINUTES)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(1000));
        return cacheManager;
    }

    /**
     * 永不过期缓存管理器
     */
    @Bean("neverExpireCacheManager")
    public CacheManager neverExpireCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(1000));
        return cacheManager;
    }

}
