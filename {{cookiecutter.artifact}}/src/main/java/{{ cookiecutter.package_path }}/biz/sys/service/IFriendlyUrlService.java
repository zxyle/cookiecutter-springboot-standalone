package {{ cookiecutter.basePackage }}.biz.sys.service;

import {{ cookiecutter.basePackage }}.biz.sys.entity.FriendlyUrl;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 友链 服务类
 */
public interface IFriendlyUrlService extends IService<FriendlyUrl> {

}
