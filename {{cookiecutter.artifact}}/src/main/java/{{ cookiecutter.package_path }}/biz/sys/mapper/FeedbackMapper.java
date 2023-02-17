package {{ cookiecutter.basePackage }}.biz.sys.mapper;

import {{ cookiecutter.basePackage }}.biz.sys.entity.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 意见反馈 Mapper 接口
 */
@Repository
public interface FeedbackMapper extends BaseMapper<Feedback> {

    // 截断表
    @Update("TRUNCATE TABLE sys_feedback")
    void truncate();

    // 创建分区表
    @Select("CREATE TABlE IF NOT EXISTS sys_feedback_${suffix} LIKE sys_feedback;")
    void cloneTable(String suffix);

    // 删除分区表
    @Select("DROP TABlE IF EXISTS sys_feedback_${suffix} ;")
    void dropTable(String suffix);

}
