package {{ cookiecutter.basePackage }}.biz.site.checkin;

import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * 签到管理
 */
@Slf4j
@RestController
@RequestMapping("/site")
@RequiredArgsConstructor
public class CheckinController extends AuthBaseController {

    final CheckinMapper thisMapper;
    final CheckinService thisService;

    /**
     * 新增签到
     */
    @LogOperation(name = "新增签到", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:checkin:add')")
    @PostMapping("/checkins")
    public R<Checkin> add() {
        if (thisService.hasCheckin(getUserId(), LocalDate.now())) {
            return R.ok("今日已签到");
        }

        thisService.checkin(getUserId(), LocalDate.now());
        return R.ok("签到成功");
    }

    /**
     * 补签
     */
    @LogOperation(name = "补签", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:checkin:patch')")
    @PostMapping("/checkins/patch")
    public R<Checkin> patch(@Valid @RequestBody PatchCheckinRequest req) {
        if (thisService.hasCheckin(getUserId(), req.getCheckinDate())) {
            return R.ok("该日已签到");
        }

        // 补签一般需要扣除积分、减掉补签次数等操作，以防止滥用
        thisService.checkin(getUserId(), req.getCheckinDate());
        return R.ok("补签成功");
    }

    /**
     * 签到日历
     */
    @LogOperation(name = "签到日历", biz = "site")
    @PreAuthorize("@ck.hasPermit('site:checkin:calendar')")
    @GetMapping("/checkins/calendar")
    public R<CheckinCalendarResponse> calendar(@Valid CalendarRequest req) {
        // 查询指定月份签到情况
        List<Integer> days = thisMapper.calendar(getUserId(), req.getFirstDayOfMonth(), req.getLastDayOfMonth());
        return R.ok(new CheckinCalendarResponse(days));
    }

}