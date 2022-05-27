package {{ cookiecutter.basePackage }}.biz.auth.mapper;

import {{ cookiecutter.basePackage }}.biz.auth.entity.Group;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户组信息 Mapper 接口
 */
@Repository
public interface GroupMapper extends BaseMapper<Group> {

    List<Group> selectAll();

    // 截断表
    void truncate();

}
