package {{ cookiecutter.basePackage }}.biz.sys.area;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 行政区 服务实现类
 */
@Service
@CacheConfig(cacheNames = "AreaCache")
public class AreaService extends ServiceImpl<AreaMapper, Area> {

    /**
     * 查询行政区
     *
     * @param code 行政区编码
     */
    @Cacheable(key = "#code", unless = "#result == null")
    public Area findAreaByCode(String code) {
        LambdaQueryWrapper<Area> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Area::getCode, Area::getName, Area::getParentId, Area::getLevel);
        wrapper.eq(StringUtils.isNotBlank(code), Area::getCode, code);
        return getOne(wrapper);
    }
}
