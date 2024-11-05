package {{ cookiecutter.basePackage }}.biz.site.info;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * 系统信息 服务实现类
 */
@Service
@CacheConfig(cacheNames = "InfoCache")
public class InfoService extends ServiceImpl<InfoMapper, Info> {

    /**
     * 根据key获取系统信息
     *
     * @param paramKey 系统信息key
     * @return 系统信息
     */
    @Caching(cacheable = {
            @Cacheable(key = "#paramKey")
    })
    public Info getByKey(String paramKey) {
        LambdaQueryWrapper<Info> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Info::getParamKey, paramKey);
        return getOne(wrapper);
    }

    /**
     * 新增系统信息
     */
    @Caching(put = {
            @CachePut(key = "#result.paramKey")
    })
    public Info insert(Info entity) {
        baseMapper.insert(entity);
        return getById(entity.getId());
    }

    /**
     * 删除系统信息
     */
    @Caching(evict = {
            @CacheEvict(key = "#paramKey")
    })
    public boolean deleteById(String paramKey) {
        LambdaQueryWrapper<Info> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Info::getParamKey, paramKey);
        return remove(wrapper);
    }

    /**
     * 更新系统信息
     */
    @Caching(put = {
            @CachePut(key = "#entity.paramKey")
    })
    public Info putById(Info entity) {
        LambdaUpdateWrapper<Info> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Info::getParamKey, entity.getParamKey());
        update(entity, wrapper);
        return entity;
    }

}
