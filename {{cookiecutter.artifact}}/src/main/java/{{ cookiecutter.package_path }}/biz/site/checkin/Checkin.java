package {{ cookiecutter.basePackage }}.biz.site.checkin;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.RecordEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 签到
 */
@Data
@TableName("site_checkin")
@EqualsAndHashCode(callSuper = false)
public class Checkin extends RecordEntity {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 签到日期
     */
    private LocalDate checkinDate;

}