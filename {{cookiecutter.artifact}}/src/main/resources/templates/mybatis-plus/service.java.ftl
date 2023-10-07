package ${table.packageName};

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
 * ${table.comment} 服务类
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "${table.className}Cache")
public class ${className} extends ServiceImpl<${table.className}Mapper, ${table.className}> {

    final ${table.className}Mapper thisMapper;

    /**
     * 新增${table.comment}（带缓存）
     */
    @CachePut(key = "#result.id", unless = "#result == null")
    public ${table.className} insert(${table.className} entity) {
        baseMapper.insert(entity);
        return entity;
    }

    /**
     * 按ID查询（查询结果不为null则缓存）
     */
    @Cacheable(key = "#id", unless = "#result == null")
    public ${table.className} queryById(Integer id) {
        return getById(id);
    }

    /**
     * 按ID删除（带缓存）
     */
    @CacheEvict(key = "#id")
    public boolean deleteById(Integer id) {
        return removeById(id);
    }

    /**
     * 按ID更新（带缓存）
     */
    @CachePut(key = "#entity.id")
    public ${table.className} putById(${table.className} entity) {
        updateById(entity);
        return getById(entity.getId());
    }

    public boolean saveBatch(List<${table.className}> list) {
        long count = list.stream().map(item -> baseMapper.insert(item)).count();
        return count == list.size();
    }

    public boolean updateBatchById(List<${table.className}> list) {
        long count = list.stream().map(item -> baseMapper.updateById(item)).count();
        return count == list.size();
    }

    public boolean removeByIds(List<Integer> ids) {
        long count = ids.stream().map(item -> baseMapper.deleteById(item)).count();
        return count == ids.size();
    }

}