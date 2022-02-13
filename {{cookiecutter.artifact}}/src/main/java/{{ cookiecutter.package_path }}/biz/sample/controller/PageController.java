package {{ cookiecutter.basePackage }}.biz.sample.controller;

import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.response.PageVO;
import {{ cookiecutter.basePackage }}.common.util.PageRequestUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页接口示例
 */
@RestController
public class PageController {

    /**
     * 分页请求
     */
    @PostMapping("/page")
    public ApiResponse<PageVO<String>> page1(PaginationRequest request) {
        IPage<Object> page = PageRequestUtil.checkForMp(request);

        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        PageVO<String> vo = new PageVO<>(list, 100L);
        ApiResponse<PageVO<String>> response = new ApiResponse<>();
        response.setData(vo);
        return response;
    }
}
