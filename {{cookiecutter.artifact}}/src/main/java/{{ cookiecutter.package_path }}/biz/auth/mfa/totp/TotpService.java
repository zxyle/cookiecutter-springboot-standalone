package {{ cookiecutter.basePackage }}.biz.auth.mfa.totp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * TOTP（基于时间的一次性密码） 服务实现类
 */
@Slf4j
@Service
public class TotpService extends ServiceImpl<TotpMapper, Totp> {

    /**
     * 按用户ID查询（查询结果不为null则缓存）
     */
    @Cacheable(cacheNames = "TotpCache", key = "#userId", unless = "#result == null")
    public Totp findByUserId(Integer userId) {
        QueryWrapper<Totp> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return getOne(wrapper);
    }

    /**
     * 按用户ID删除（带缓存）
     */
    @CacheEvict(cacheNames = "TotpCache", key = "#userId")
    public boolean deleteByUserId(Integer userId) {
        QueryWrapper<Totp> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return remove(wrapper);
    }

}
