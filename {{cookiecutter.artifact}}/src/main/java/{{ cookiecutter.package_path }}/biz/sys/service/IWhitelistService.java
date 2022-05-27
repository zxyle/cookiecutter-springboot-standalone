package {{ cookiecutter.basePackage }}.biz.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Whitelist;

/**
 * IP黑名单 服务类
 */
public interface IWhitelistService extends IService<Whitelist> {

    /**
     * 按ID查询
     */
    Whitelist queryById(Long id);

    /**
     * 分页查询
     */
    IPage<Whitelist> pageQuery(IPage<Whitelist> page);

}
