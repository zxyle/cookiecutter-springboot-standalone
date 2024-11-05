package {{ cookiecutter.basePackage }}.biz.sys.setting;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * 系统设置 服务实现类
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "SettingCache", cacheManager = "neverExpireCacheManager")
public class SettingService extends ServiceImpl<SettingMapper, Setting> {

    /**
     * 按ID查询
     */
    @Cacheable(key = "#id")
    public Setting findById(Integer id) {
        return getById(id);
    }

    /**
     * 按名称查询
     *
     * @param label 选项名称
     */
    @Cacheable(key = "#label")
    public Item get(String label) {
        LambdaQueryWrapper<Setting> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Setting::getOptionLabel, Setting::getOptionValue, Setting::getDataType);
        wrapper.eq(Setting::getOptionLabel, label);
        Setting one = getOne(wrapper);
        if (one != null) {
            return new Item(one);
        }
        return null;
    }

    /**
     * 更新选项
     *
     * @param label 选项名称
     * @param value 选项新值
     */
    @CachePut(key = "#label")
    public Item update(String label, String value) {
        LambdaUpdateWrapper<Setting> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Setting::getOptionLabel, label);
        wrapper.set(Setting::getOptionValue, value);
        boolean updated = update(wrapper);
        if (updated) {
            LambdaQueryWrapper<Setting> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.select(Setting::getOptionLabel, Setting::getOptionValue, Setting::getDataType);
            wrapper2.eq(Setting::getOptionLabel, label);
            Setting one = getOne(wrapper2);
            return new Item(one);
        }
        return null;
    }

    /**
     * 按ID删除（带缓存）
     *
     * @param label 选项名称，用于清除缓存
     * @param id    选项ID
     */
    @Caching(evict = {
            @CacheEvict(key = "#label"),
            @CacheEvict(key = "#id")
    })
    public boolean deleteById(String label, Integer id) {
        return removeById(id);
    }
}
