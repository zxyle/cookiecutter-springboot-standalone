package {{ cookiecutter.basePackage }}.biz.sys.mapper;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Info;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统信息 Mapper 接口
 */
@Repository
public interface InfoMapper extends BaseMapper<Info> {

    List<Info> selectAll();

    // 截断表
    void truncate();

}
