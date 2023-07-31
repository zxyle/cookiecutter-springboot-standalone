package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * ${table.comment!} 服务实现类
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "${entity}Cache")
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    /**
     * 新增${table.comment!}（带缓存）
     */
    @CachePut(key = "#result.id", unless = "#result == null")
    @Override
    public ${entity} insert(${entity} entity) {
        save(entity);
        return entity;
    }

    /**
     * 按ID查询（查询结果不为null则缓存）
     */
    @Cacheable(key = "#id", unless = "#result == null")
    @Override
    public ${entity} queryById(Long id) {
        return getById(id);
    }

    /**
     * 按ID更新（带缓存）
     */
    @CachePut(key = "#entity.id")
    @Override
    public ${entity} putById(${entity} entity) {
        updateById(entity);
        return getById(entity.getId());
    }

    /**
     * 按ID删除（带缓存）
     */
     @CacheEvict(key = "#id")
     @Override
     public boolean deleteById(Long id) {
         return removeById(id);
     }

}
