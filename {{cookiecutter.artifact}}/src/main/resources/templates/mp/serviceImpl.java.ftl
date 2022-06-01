package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * ${table.comment!} 服务实现类
 */
@Slf4j
@Service
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    /**
     * 按ID查询
     */
    @Cacheable(cacheNames = "${entity}Cache", key = "#id")
    @Override
    public ${entity} queryById(Long id) {
        return getById(id);
    }

    /**
     * 分页查询
     */
    @Cacheable(cacheNames = "${entity}Cache", key = "#p.getCurrent()+#p.getSize()")
    @Override
    public IPage<${entity}> pageQuery(IPage<${entity}> p) {
        return page(p);
    }

}
