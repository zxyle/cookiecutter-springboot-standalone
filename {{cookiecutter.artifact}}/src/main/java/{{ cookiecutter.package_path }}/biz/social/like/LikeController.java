package {{ cookiecutter.basePackage }}.biz.social.like;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import {{ cookiecutter.namespace }}.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 点赞管理
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/social")
public class LikeController extends AuthBaseController {

    final LikeService likeService;

    /**
     * 查看自己点赞列表
     */
    @GetMapping("/likes")
    public R<Page<LikeDTO>> page(@Valid LikePageRequest req) {
        Page<LikeDTO> page = likeService.getResIdList(req.getResType(), getUserId(), req.getPageNum(), req.getPageSize());
        List<Integer> list = page.getRecords().stream().map(LikeDTO::getResId).{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %};
        // TODO 查询资源信息 补充LikeDTO的res属性
        return R.ok(page);
    }

    /**
     * 点赞，返回当前点赞数
     */
    @GetMapping("/like")
    public R<Long> like(@Valid LikeRequest req) {
        Long likeCnt = likeService.like(req.getResType(), req.getResId(), getUserId());
        return R.ok(likeCnt);
    }

    /**
     * 取消点赞，返回当前点赞数
     */
    @GetMapping("/unlike")
    public R<Long> unlike(@Valid LikeRequest req) {
        Long likeCnt = likeService.unlike(req.getResType(), req.getResId(), getUserId());
        return R.ok(likeCnt);
    }
}
