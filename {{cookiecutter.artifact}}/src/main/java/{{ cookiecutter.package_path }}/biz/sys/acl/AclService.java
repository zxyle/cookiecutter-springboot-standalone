package {{ cookiecutter.basePackage }}.biz.sys.acl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * IP访问控制 服务类
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "AclCache")
public class AclService extends ServiceImpl<AclMapper, Acl> {

    final AclMapper thisMapper;

    /**
     * 新增IP访问控制（带缓存）
     */
    @CachePut(key = "#result.id", unless = "#result == null")
    public Acl insert(Acl entity) {
        baseMapper.insert(entity);
        return null;
    }

    /**
     * 按ID查询（查询结果不为null则缓存）
     */
    @Cacheable(key = "#id", unless = "#result == null")
    public Acl queryById(Integer id) {
        return baseMapper.selectById(id);
    }

    /**
     * 按ID删除（带缓存）
     */
    @CacheEvict(key = "#id")
    public boolean deleteById(Integer id) {
        int i = baseMapper.deleteById(id);
        return i > 0;
    }

    /**
     * 按ID更新（带缓存）
     */
    @CachePut(key = "#entity.id")
    public Acl putById(Acl entity) {
        updateById(entity);
        return getById(entity.getId());
    }

    @Cacheable(key = "'all'")
    public List<Acl> findAllIp() {
        QueryWrapper<Acl> wrapper = new QueryWrapper<>();
        wrapper.select("ip", "allowed", "end_time");
        return list(wrapper);
    }

}