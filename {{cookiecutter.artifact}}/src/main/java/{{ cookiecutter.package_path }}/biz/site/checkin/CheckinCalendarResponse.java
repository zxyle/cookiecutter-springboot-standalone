package {{ cookiecutter.basePackage }}.biz.site.checkin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 签到日历
 */
@Data
@AllArgsConstructor
public class CheckinCalendarResponse {

    /**
     * 本月签到情况
     */
    private List<Integer> checkinDays;

    /**
     * 连续签到天数
     */
    // private int continuousDays;
}
