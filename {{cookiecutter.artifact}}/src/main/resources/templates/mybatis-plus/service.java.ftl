package ${table.packageName};

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
 * ${table.comment} 服务类
<#if author??>
 *
 * @author ${author}
</#if>
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "${table.className}Cache")
public class ${className} extends ServiceImpl<${table.className}Mapper, ${table.className}> {

    /**
     * 新增${table.comment}
     */
    @CachePut(key = "#result.id")
    public ${table.className} insert(${table.className} entity) {
        baseMapper.insert(entity);
        // 有些字段是数据库默认值，直接返回entity是没有的，所以需要重新查询
        return getById(entity.getId());
    }

    /**
     * 按ID查询${table.comment}
     */
    @Cacheable(key = "#id")
    public ${table.className} findById(Integer id) {
        return getById(id);
    }

    /**
     * 查询指定用户的${table.comment}
     */
    @Cacheable(key = "#id + '_' + #userId")
    public ${table.className} findById(Integer id, Integer userId) {
        LambdaQueryWrapper<${table.className}> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(id != null, ${table.className}::getId, id);
        // wrapper.eq(userId != null, ${table.className}::getUserId, userId);
        return getOne(wrapper);
    }

    /**
     * 按ID删除${table.comment}
     */
    @CacheEvict(key = "#id")
    public boolean deleteById(Integer id) {
        return removeById(id);
    }

    /**
     * 删除指定用户的${table.comment}
     */
    @CacheEvict(key = "#id + '_' + #userId")
    public boolean deleteById(Integer id, Integer userId) {
        LambdaQueryWrapper<${table.className}> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(id != null, ${table.className}::getId, id);
        // wrapper.eq(userId != null, ${table.className}::getUserId, userId);
        return remove(wrapper);
    }

    /**
     * 按ID更新${table.comment}
     */
    @CachePut(key = "#entity.id")
    public ${table.className} putById(${table.className} entity) {
        updateById(entity);
        return getById(entity.getId());
    }

}