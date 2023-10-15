package {{ cookiecutter.basePackage }}.biz.auth.mfa.totp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * TOTP（基于时间的一次性密码） 服务实现类
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "TotpCache")
public class TotpService extends ServiceImpl<TotpMapper, Totp> {

    /**
     * 新增TOTP（带缓存）
     */
    @CachePut(key = "#entity.userId")
    public Totp insert(Totp entity) {
        baseMapper.insert(entity);
        return entity;
    }

    /**
     * 按用户ID查询
     */
    @Cacheable(key = "#userId")
    public Totp findByUserId(Integer userId) {
        QueryWrapper<Totp> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return getOne(wrapper);
    }

    /**
     * 按用户ID删除（带缓存）
     */
    @CacheEvict(key = "#userId")
    public boolean deleteByUserId(Integer userId) {
        QueryWrapper<Totp> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return remove(wrapper);
    }

}
