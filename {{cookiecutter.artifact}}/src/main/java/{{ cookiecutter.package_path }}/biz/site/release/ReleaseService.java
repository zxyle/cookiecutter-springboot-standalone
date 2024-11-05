package {{ cookiecutter.basePackage }}.biz.site.release;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 发布版本 服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "ReleaseCache")
public class ReleaseService extends ServiceImpl<ReleaseMapper, Release> {

    /**
     * 新增发布版本
     */
    @CachePut(key = "#result.id")
    public Release insert(Release entity) {
        baseMapper.insert(entity);
        return entity;
    }

    /**
     * 按ID查询
     */
    @Cacheable(key = "#id")
    public Release findById(Integer id) {
        return getById(id);
    }

    /**
     * 按ID删除
     */
    @CacheEvict(key = "#id")
    public boolean deleteById(Integer id) {
        return removeById(id);
    }

    /**
     * 按ID更新
     */
    @CachePut(key = "#entity.id")
    public Release putById(Release entity) {
        updateById(entity);
        return getById(entity.getId());
    }
}
