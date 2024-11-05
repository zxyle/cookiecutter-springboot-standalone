package {{ cookiecutter.basePackage }}.biz.sys.verification;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码发送记录 Mapper 接口
 */
@Mapper
public interface VerificationMapper extends BaseMapper<Verification> {

}
