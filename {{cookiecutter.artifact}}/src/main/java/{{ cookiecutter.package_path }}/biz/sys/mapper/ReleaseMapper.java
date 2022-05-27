package {{ cookiecutter.basePackage }}.biz.sys.mapper;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Release;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 发布版本 Mapper 接口
 */
@Repository
public interface ReleaseMapper extends BaseMapper<Release> {

    List<Release> selectAll();

    // 截断表
    void truncate();

}
