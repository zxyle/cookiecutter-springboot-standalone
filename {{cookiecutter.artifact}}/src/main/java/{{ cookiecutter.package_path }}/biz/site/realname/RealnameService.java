package {{ cookiecutter.basePackage }}.biz.site.realname;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 实名认证 服务类
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "RealnameCache")
public class RealnameService extends ServiceImpl<RealnameMapper, Realname> {

    /**
     * 新增实名认证（带缓存）
     */
    @CachePut(key = "#result.userId", unless = "#result == null")
    public Realname insert(Realname entity) {
        baseMapper.insert(entity);
        return entity;
    }

    /**
     * 按用户ID查询（查询结果不为null则缓存）
     */
    @Cacheable(key = "#userId", unless = "#result == null")
    public Realname findByUserId(Integer userId) {
        LambdaQueryWrapper<Realname> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Realname::getUserId, userId);
        return getOne(wrapper);
    }

}