package {{ cookiecutter.basePackage }}.biz.site.checkin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 签到 数据访问类
 */
@Mapper
public interface CheckinMapper extends BaseMapper<Checkin> {

    /**
     * 获取用户某月的签到情况
     *
     * @param userId    用户ID
     * @param startDate 开始日期
     * @param endDate   结束日期
     */
    List<Integer> calendar(Integer userId, LocalDate startDate, LocalDate endDate);

}