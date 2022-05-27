package {{ cookiecutter.basePackage }}.biz.sys.service;

import {{ cookiecutter.basePackage }}.biz.sys.entity.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 登录日志 服务类
 */
public interface ILoginLogService extends IService<LoginLog> {

}
