package {{ cookiecutter.basePackage }}.biz.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Sql;
import {{ cookiecutter.basePackage }}.biz.sys.mapper.SqlMapper;
import {{ cookiecutter.basePackage }}.biz.sys.service.ISqlService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * SQL执行 服务实现类
 */
@Service
public class SqlServiceImpl extends ServiceImpl<SqlMapper, Sql> implements ISqlService {

    /**
     * 按ID查询
     */
    @Cacheable(cacheNames = "SqlCache", key = "#id")
    @Override
    public Sql queryById(Long id) {
        return getById(id);
    }

    /**
     * 分页查询
     */
    @Cacheable(cacheNames = "SqlCache", key = "#p.getCurrent()+#p.getSize()")
    @Override
    public IPage<Sql> pageQuery(IPage<Sql> p) {
        return page(p);
    }

}
