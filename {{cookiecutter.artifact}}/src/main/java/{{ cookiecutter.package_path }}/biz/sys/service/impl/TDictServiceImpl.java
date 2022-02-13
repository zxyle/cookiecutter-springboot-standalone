package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import {{ cookiecutter.basePackage }}.biz.sys.entity.TDict;
import {{ cookiecutter.basePackage }}.biz.sys.mapper.TDictMapper;
import {{ cookiecutter.basePackage }}.biz.sys.request.DictRequest;
import {{ cookiecutter.basePackage }}.biz.sys.service.ITDictService;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import {{ cookiecutter.basePackage }}.common.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 字典数据表 服务实现类
 */
@Service
public class TDictServiceImpl extends ServiceImpl<TDictMapper, TDict> implements ITDictService {

    @Override
    public IPage<TDict> getPageList(DictRequest request) {
        Integer pageNum = PageRequestUtil.checkPageNum(request.getPageNum());
        Integer pageSize = PageRequestUtil.checkPageSize(request.getPageSize());
        Page<TDict> page = new Page<>(pageNum, pageSize);
        QueryWrapper<TDict> queryWrapper = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(request.getId())) {
            queryWrapper.eq("id", request.getId());
        }
        queryWrapper.orderByDesc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }
}
