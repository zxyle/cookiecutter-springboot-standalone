package {{ cookiecutter.basePackage }}.biz.auth.app;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 应用 服务实现类
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "AppCache")
public class AppService extends ServiceImpl<AppMapper, App> {

    /**
     * 新增应用（带缓存）
     */
    @CachePut(key = "#result.id", unless = "#result == null")
    public App insert(App entity) {
        save(entity);
        return entity;
    }

    /**
     * 按ID查询（查询结果不为null则缓存）
     */
    @Cacheable(key = "#id", unless = "#result == null")
    public App findById(Integer id) {
        return getById(id);
    }

    /**
     * 按ID更新（带缓存）
     */
    @CachePut(key = "#entity.id")
    public App putById(App entity) {
        updateById(entity);
        return getById(entity.getId());
    }

    /**
     * 按ID删除（带缓存）
     */
    @CacheEvict(key = "#id")
    public boolean deleteById(Integer id) {
        return removeById(id);
    }

}
