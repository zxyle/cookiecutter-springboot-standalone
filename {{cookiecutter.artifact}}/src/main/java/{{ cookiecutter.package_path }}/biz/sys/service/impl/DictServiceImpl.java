package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Dict;
import {{ cookiecutter.basePackage }}.biz.sys.mapper.DictMapper;
import {{ cookiecutter.basePackage }}.biz.sys.service.IDictService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典数据表 服务实现类
 */
@Service
@CacheConfig(cacheNames = "dictCache")
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    /**
     * 查询指定字典
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     */
    @Override
    @Cacheable(key = "#dictType+#dictValue")
    public Dict queryDict(String dictType, String dictValue) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type", dictType);
        queryWrapper.eq("value", dictValue);
        return getOne(queryWrapper);
    }

    /**
     * 按dict_type查询所有
     *
     * @param dictType 字典类型
     */
    @Override
    @Cacheable(key = "#dictType")
    public List<Dict> listAllDicts(String dictType) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.select("label, value");
        wrapper.eq("dict_type", dictType);
        return list(wrapper);
    }
}
