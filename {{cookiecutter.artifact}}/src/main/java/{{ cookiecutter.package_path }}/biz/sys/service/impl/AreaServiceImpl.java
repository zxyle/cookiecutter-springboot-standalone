package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Area;
import {{ cookiecutter.basePackage }}.biz.sys.mapper.AreaMapper;
import {{ cookiecutter.basePackage }}.biz.sys.service.IAreaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 行政区 服务实现类
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

    @Override
    public Area getAreaByCode(String code) {
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.select("code, name, parent, level");
        wrapper.eq(StringUtils.isNotBlank(code), "code", code);
        return getOne(wrapper);
    }
}
