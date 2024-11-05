package {{ cookiecutter.basePackage }}.biz.auth.group;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户组信息 Mapper 接口
 */
@Mapper
public interface GroupMapper extends BaseMapper<Group> {

    /**
     * 查询最大排序号
     *
     * @param parentId 父级ID
     * @return 最大排序号
     */
    Integer findMaxSortNum(Integer parentId);

}
