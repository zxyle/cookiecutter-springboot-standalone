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
        return null;
    }

    /**
     * 按ID查询（查询结果不为null则缓存）
     */
    @Cacheable(key = "#id", unless = "#result == null")
    public ${table.className} queryById(Integer id) {
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
    public ${table.className} putById(${table.className} entity) {
        updateById(entity);
        return getById(entity.getId());
    }

    public boolean saveBatch(List<${table.className}> list) {
        long count = list.stream().map(e -> baseMapper.insert(e)).count();
        return count == list.size();
    }

    public boolean updateBatchById(List<${table.className}> list) {
        return true;
    }

    public boolean removeByIds(List<Integer> ids) {
        ids.forEach(this::deleteById);
        return true;
    }

}