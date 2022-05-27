package {{ cookiecutter.basePackage }}.biz.sys.mapper;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Blacklist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 模板表 Mapper 接口
 */
@Repository
public interface IpBlacklistMapper extends BaseMapper<Blacklist> {

    List<Blacklist> selectAll();

    // 截断表
    void truncate();

}
