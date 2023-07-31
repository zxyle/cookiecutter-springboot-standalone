package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
 * ${table.comment!} 服务类
 */
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    /**
     * 新增${table.comment!}（带缓存）
     */
    ${entity} insert(${entity} entity);

    /**
     * 按ID查询（带缓存）
     */
    ${entity} queryById(Long id);

    /**
     * 按ID更新（带缓存）
     */
    ${entity} putById(${entity} entity);

    /**
     * 按ID删除（带缓存）
     */
    boolean deleteById(Long id);

}
