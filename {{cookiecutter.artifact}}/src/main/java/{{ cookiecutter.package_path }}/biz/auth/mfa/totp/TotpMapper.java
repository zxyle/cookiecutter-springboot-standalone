package {{ cookiecutter.basePackage }}.biz.auth.mfa.totp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * TOTP（基于时间的一次性密码） Mapper 接口
 */
@Mapper
public interface TotpMapper extends BaseMapper<Totp> {

}
