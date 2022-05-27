package {{ cookiecutter.basePackage }}.biz.sys.service;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Info;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统信息 服务类
 */
public interface IInfoService extends IService<Info> {

}
