package {{ cookiecutter.basePackage }}.biz.sys.dict;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典数据表 服务实现类
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "DictCache")
public class DictService extends ServiceImpl<DictMapper, Dict> {

    /**
     * 按字典类型查询所有字典
     *
     * @param dictType 字典类型
     */
    @Cacheable(key = "#dictType", unless = "#result == null")
    public List<Dict> findDictsByType(String dictType) {
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Dict::getId, Dict::getLabel, Dict::getValue);
        wrapper.eq(Dict::getDictType, dictType);
        return list(wrapper);
    }

    /**
     * 新增字典条目（带缓存）
     */
    @CacheEvict(key = "#entity.dictType")
    public Dict insert(Dict entity) {
        save(entity);
        return entity;
    }

    /**
     * 删除字典条目
     *
     * @param dictType 字典类型，用于清除缓存
     */
    @CacheEvict(key = "#dictType")
    public boolean deleteDict(String dictType, Integer id) {
        log.info("删除字典类型 {} 的字典条目 {}", dictType, id);
        return removeById(id);
    }

    /**
     * 更新字典条目
     */
    @CacheEvict(key = "#entity.dictType")
    public boolean updateDict(Dict entity) {
        return updateById(entity);
    }

    /**
     * 删除字典类型
     *
     * @param dictType 字典类型
     */
    @CacheEvict(key = "#dictType")
    public boolean deleteByDictType(String dictType) {
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dict::getDictType, dictType);
        return remove(wrapper);
    }
}
