package {{ cookiecutter.basePackage }}.biz.site.rank;

import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.List;

/**
 * 排行榜管理
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/site/rank")
public class RankController {

    final RankService rankService;

    /**
     * 获取排行榜
     */
    @GetMapping("/top")
    public R<List<TopResponse>> top(@Valid TopRequest req) {
        List<TopResponse> members = rankService.getTopMember(req.getBiz(), req.getSize());
        // TODO 获取member其他信息，比如用户昵称，文章标题等
        return R.ok(members);
    }

}
