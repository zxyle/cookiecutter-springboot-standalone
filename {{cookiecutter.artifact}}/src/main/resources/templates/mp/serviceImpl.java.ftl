package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * ${table.comment!} 服务实现类
 */
@Slf4j
@Service
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    /**
     * 新增${table.comment!}（带缓存）
     */
    @CachePut(cacheNames = "${entity}Cache", key = "#result.id", unless = "#result == null")
    @Override
    public ${entity} insert(${entity} entity) {
        save(entity);
        return entity;
    }

    /**
     * 按ID查询（查询结果不为null则缓存）
     */
    @Cacheable(cacheNames = "${entity}Cache", key = "#id", unless = "#result == null")
    @Override
    public ${entity} queryById(Long id) {
        return getById(id);
    }

    /**
     * 按ID更新（带缓存）
     */
    @CachePut(cacheNames = "${entity}Cache", key = "#entity.id")
    @Override
    public ${entity} putById(${entity} entity) {
        updateById(entity);
        return getById(entity.getId());
    }

    /**
     * 按ID删除（带缓存）
     */
     @CacheEvict(cacheNames = "${entity}Cache", key = "#id")
     @Override
     public boolean deleteById(Long id) {
         return removeById(id);
     }

    /**
     * 分页查询（带缓存）
     */
    @Override
    public IPage<${entity}> pageQuery(IPage<${entity}> p, QueryWrapper<${entity}> wrapper) {
        return page(p, wrapper);
    }

}
