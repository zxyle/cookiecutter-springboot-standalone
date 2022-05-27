package {{ cookiecutter.basePackage }}.biz.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Sql;

/**
 * SQL执行 服务类
 */
public interface ISqlService extends IService<Sql> {

    /**
     * 按ID查询
     */
    Sql queryById(Long id);

    /**
     * 分页查询
     */
    IPage<Sql> pageQuery(IPage<Sql> page);

}
