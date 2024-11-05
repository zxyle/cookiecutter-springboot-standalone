package {{ cookiecutter.basePackage }}.biz.sys.verification;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 验证码发送记录 服务实现类
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "VerificationCache")
public class VerificationService extends ServiceImpl<VerificationMapper, Verification> {

    /**
     * 按ID查询
     */
    @Cacheable(key = "#id", unless = "#result == null")
    public Verification findById(Integer id) {
        return getById(id);
    }

}
