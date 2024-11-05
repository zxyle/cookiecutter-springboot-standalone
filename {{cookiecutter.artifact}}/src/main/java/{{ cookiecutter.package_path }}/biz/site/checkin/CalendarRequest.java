package {{ cookiecutter.basePackage }}.biz.site.checkin;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * 签到日历请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CalendarRequest extends BaseRequest {

    /**
     * 查询年月 yyyy-MM，不传默认当月
     */
    private YearMonth yearMonth = YearMonth.now();

    /**
     * 获取月份第一天
     */
    public LocalDate getFirstDayOfMonth() {
        return yearMonth.atDay(1);
    }

    /**
     * 获取月份最后一天
     */
    public LocalDate getLastDayOfMonth() {
        return yearMonth.atEndOfMonth();
    }

}
