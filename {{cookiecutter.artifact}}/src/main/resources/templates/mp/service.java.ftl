package ${package.Service};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
 * ${table.comment!} 服务类
 */
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    /**
     * 按ID查询
     */
    ${entity} queryById(Long id);

    /**
     * 分页查询
     */
    IPage<${entity}> pageQuery(IPage<${entity}> page);

}
