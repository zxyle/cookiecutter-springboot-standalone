package {{ cookiecutter.basePackage }}.biz.sys.service;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Blacklist;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * IP黑名单 服务类
 */
public interface IIpBlacklistService extends IService<Blacklist> {

}
