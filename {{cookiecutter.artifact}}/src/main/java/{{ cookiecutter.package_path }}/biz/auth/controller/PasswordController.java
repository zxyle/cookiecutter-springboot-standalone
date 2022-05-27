package {{ cookiecutter.basePackage }}.biz.auth.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import {{ cookiecutter.basePackage }}.biz.auth.entity.User;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.UserMapper;
import {{ cookiecutter.basePackage }}.biz.user.request.password.ModifyByOldRequest;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.AntdProResponse;
import dev.zhengxiang.tool.crypto.Werkzeug;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 密码管理
 */
@RestController
@RequestMapping("/password")
@Slf4j
@SaCheckLogin
public class PasswordController extends AuthBaseController {

    @Autowired
    UserMapper userMapper;

    Werkzeug werkzeug = new Werkzeug();

    /**
     * 修改密码
     */
    @PostMapping("/modify")
    public AntdProResponse modify(@Valid @RequestBody ModifyByOldRequest request) {
        User user = getCurrentUser();

        AntdProResponse response = new AntdProResponse();
        if (null != user && werkzeug.checkPasswordHash(user.getPwd(), request.getOldPassword())) {
            User newUser = new User();
            newUser.setPwd(werkzeug.generatePasswordHash(request.getNewPassword()));
            newUser.setId(user.getId());
            userMapper.updateById(newUser);
            StpUtil.logout();
            response.setSuccess(true);
            return response;
        }

        return response;
    }

    /**
     * 忘记密码(找回密码)
     */
    @PostMapping("/forget")
    public void forget() {

    }

    /**
     * 重置密码
     */
    @PostMapping("/reset")
    public void reset() {

    }


}
