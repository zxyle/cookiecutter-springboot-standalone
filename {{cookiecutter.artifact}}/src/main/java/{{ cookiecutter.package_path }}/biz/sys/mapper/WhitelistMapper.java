package {{ cookiecutter.basePackage }}.biz.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import {{ cookiecutter.basePackage }}.biz.sys.entity.Whitelist;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * IP黑名单 Mapper 接口
 */
@Repository
public interface WhitelistMapper extends BaseMapper<Whitelist> {

    List<Whitelist> selectAll();

    // 截断表
    void truncate();

}
