package {{ cookiecutter.basePackage }}.biz.site.feedback;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 意见反馈 服务实现类
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "FeedbackCache")
public class FeedbackService extends ServiceImpl<FeedbackMapper, Feedback> {

    /**
     * 按ID查询（带缓存）
     */
    @Cacheable(key = "#id", unless = "#result == null")
    public Feedback findById(Integer id) {
        return getById(id);
    }

    /**
     * 按ID更新（带缓存）
     */
    @CachePut(key = "#feedback.id")
    public Feedback putById(Feedback feedback) {
        updateById(feedback);
        return getById(feedback.getId());
    }

    /**
     * 按ID删除（带缓存）
     */
    @CacheEvict(key = "#id")
    public boolean deleteById(Integer id) {
        return removeById(id);
    }

}
