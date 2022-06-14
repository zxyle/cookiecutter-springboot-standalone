package {{ cookiecutter.basePackage }}.biz.sys.mapper;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 字典数据表 Mapper 接口
 */
public interface DictMapper extends BaseMapper<Dict> {

    // 截断表
    void truncate();

}
