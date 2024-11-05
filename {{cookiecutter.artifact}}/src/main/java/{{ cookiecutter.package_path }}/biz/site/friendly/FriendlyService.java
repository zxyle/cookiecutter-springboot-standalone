package {{ cookiecutter.basePackage }}.biz.site.friendly;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链 服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "FriendlyCache")
public class FriendlyService extends ServiceImpl<FriendlyMapper, Friendly> {

    /**
     * 新增友链（带缓存）
     */
    @CachePut(key = "#result.id")
    @CacheEvict(key = "'list'")
    public Friendly insert(Friendly entity) {
        baseMapper.insert(entity);
        return entity;
    }

    /**
     * 按ID查询
     */
    @Cacheable(key = "#id")
    public Friendly findById(Integer id) {
        return getById(id);
    }

    /**
     * 按ID删除（带缓存）
     */
    @CacheEvict(allEntries = true)
    public boolean deleteById(Integer id) {
        return removeById(id);
    }

    /**
     * 按ID更新（带缓存）
     */
    @CachePut(key = "#entity.id")
    @CacheEvict(key = "'list'")
    public Friendly putById(Friendly entity) {
        updateById(entity);
        return getById(entity.getId());
    }

    /**
     * 获取友链列表
     */
    @Cacheable(key = "'list'")
    public List<Friendly> all() {
        LambdaQueryWrapper<Friendly> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Friendly::getId, Friendly::getContent, Friendly::getUrl);
        wrapper.eq(Friendly::getEnabled, true);
        wrapper.orderByAsc(Friendly::getSort);
        return list(wrapper);
    }

}
