package {{ cookiecutter.basePackage }}.biz.site.checkin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 签到 响应类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CheckinResponse {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 签到日期
     */
    private LocalDate checkinDate;

}