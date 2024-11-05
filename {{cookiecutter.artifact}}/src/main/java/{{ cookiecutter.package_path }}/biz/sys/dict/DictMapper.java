package {{ cookiecutter.basePackage }}.biz.sys.dict;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典数据表 Mapper 接口
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 查询最大排序号
     *
     * @param dictType 字典类型
     * @return 排序号
     */
    Integer findMaxSortNum(String dictType);
}
