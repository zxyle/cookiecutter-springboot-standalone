package {{ cookiecutter.basePackage }}.biz.auth.password.history;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 密码历史表 Mapper 接口
 */
@Mapper
public interface PasswordHistoryMapper extends BaseMapper<PasswordHistory> {

}
