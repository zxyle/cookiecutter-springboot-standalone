package {{ cookiecutter.basePackage }}.biz.social.star;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;

/**
 * 收藏管理
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/social")
public class StarController extends AuthBaseController {

    final StarService starService;

    /**
     * 收藏，返回当前收藏数量
     */
    @PostMapping("/star")
    public R<Long> star(@Valid @RequestBody StarRequest req) {
        Long currentStar = starService.star(req.getResType(), req.getResId(), getUserId());
        return R.ok(currentStar);
    }

    /**
     * 取消收藏，返回当前收藏数量
     */
    @DeleteMapping("/unstar")
    public R<Long> unstar(@Valid StarRequest req) {
        Long currentStar = starService.unstar(req.getResType(), req.getResId(), getUserId());
        return R.ok(currentStar);
    }

    /**
     * 收藏列表
     *
     * @param resType 资源类型
     */
    @GetMapping("/stars")
    public R<Page<StarDTO>> starList(Integer resType, PaginationRequest req) {
        Page<StarDTO> page = starService.getResIdList(resType, getUserId(), req);
        return R.ok(page);
    }

}
