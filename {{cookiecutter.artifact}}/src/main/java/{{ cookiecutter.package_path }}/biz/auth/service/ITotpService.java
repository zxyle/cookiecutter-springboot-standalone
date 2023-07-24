package {{ cookiecutter.basePackage }}.biz.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import {{ cookiecutter.basePackage }}.biz.auth.entity.Totp;

/**
 * TOTP（基于时间的一次性密码） 服务类
 */
public interface ITotpService extends IService<Totp> {

    /**
     * 按用户ID查询（带缓存）
     */
    Totp queryByUserId(Long userId);

    /**
     * 按用户ID删除（带缓存）
     */
    boolean deleteByUserId(Long userId);

}
