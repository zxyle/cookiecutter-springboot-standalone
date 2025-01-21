package {{ cookiecutter.basePackage }}.biz.site.banner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 轮播图服务类
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "BannerCache")
public class BannerService extends ServiceImpl<BannerMapper, Banner> {

    final BannerMapper thisMapper;

    /**
     * 新增
     */
    @CachePut(key = "#result.id")
    public Banner insert(Banner entity) {
        baseMapper.insert(entity);
        return entity;
    }

    /**
     * 按ID查询
     */
    @Cacheable(key = "#id")
    public Banner findById(Integer id) {
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
     * 删除指定用户的数据
     */
    @CacheEvict(key = "#id + '_' + #userId")
    public boolean deleteById(Integer id, Integer userId) {
        LambdaQueryWrapper<Banner> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(id != null, Banner::getId, id);
        // wrapper.eq(userId != null, Banner::getUserId, userId);
        return remove(wrapper);
    }

    /**
     * 按ID更新
     */
    @CachePut(key = "#entity.id")
    public Banner putById(Banner entity) {
        updateById(entity);
        return getById(entity.getId());
    }


}