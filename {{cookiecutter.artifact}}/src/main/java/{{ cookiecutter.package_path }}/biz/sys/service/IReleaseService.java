package {{ cookiecutter.basePackage }}.biz.sys.service;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Release;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 发布版本 服务类
 */
public interface IReleaseService extends IService<Release> {

}
