package {{ cookiecutter.basePackage }}.biz.sys.mapper;

import {{ cookiecutter.basePackage }}.biz.sys.entity.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 登录日志 Mapper 接口
 */
@Repository
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    List<LoginLog> selectAll();

    // 截断表
    void truncate();

}
