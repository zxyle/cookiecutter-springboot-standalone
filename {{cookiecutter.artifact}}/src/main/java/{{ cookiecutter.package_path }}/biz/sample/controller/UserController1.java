package {{ cookiecutter.basePackage }}.biz.sample.controller;

import {{ cookiecutter.basePackage }}.common.request.PageRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.biz.sample.entity.User;
import {{ cookiecutter.basePackage }}.biz.sample.service.UserService;
import {{ cookiecutter.basePackage }}.common.utils.PageRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user1")
public class UserController1 {

    private final UserService userService;

    @Autowired
    public UserController1(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/list")
    public ResponseEntity<Object> list(@RequestBody PageRequest request) {
        Integer pageNum = PageRequestUtils.checkPageNum(request.getPageNum());
        Integer pageSize = PageRequestUtils.checkPageSize(request.getPageSize());

        ApiResponse response = userService.list(pageNum, pageSize);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody User user) {
        ApiResponse response = userService.save(user);
        return ResponseEntity.ok(response);
    }
}
