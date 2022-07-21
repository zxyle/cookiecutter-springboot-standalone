package {{ cookiecutter.basePackage }}.biz.auth.controller;

import {{ cookiecutter.basePackage }}.biz.auth.constant.AuthConst;
import {{ cookiecutter.basePackage }}.biz.auth.request.login.LoginByNameRequest;
import {{ cookiecutter.basePackage }}.biz.auth.response.LoginResponse;
import {{ cookiecutter.basePackage }}.biz.auth.security.LoginUser;
import {{ cookiecutter.basePackage }}.biz.auth.service.LoginService;
import {{ cookiecutter.basePackage }}.biz.auth.util.JwtUtil;
import {{ cookiecutter.basePackage }}.biz.sys.entity.LoginLog;
import {{ cookiecutter.basePackage }}.biz.sys.service.ILoginLogService;
import {{ cookiecutter.basePackage }}.biz.sys.service.VerifyService;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.util.IpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

/**
 * 登录管理
 */
@RestController
@RequestMapping("/auth/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginController {

    final StringRedisTemplate stringRedisTemplate;

    final ILoginLogService loginLogService;

    final LoginService loginService;

    final AuthenticationManager authenticationManager;

    final VerifyService verifyService;


    /**
     * 用户登录
     *
     * @param request 用户信息
     * @apiNote 通过用户名和密码进行用户登录
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginByNameRequest request, HttpServletRequest servletRequest) {
        String loginName = request.getLoginName();

        // 如需开启验证码，请取消注释下面代码
        // verifyService.verify(request.getCode(), request.getCaptchaId());

        // 登录日志
        LoginLog loginLog = new LoginLog();
        loginLog.setUa(servletRequest.getHeader(HttpHeaders.USER_AGENT));
        loginLog.setIp(IpUtil.getIpAddr(servletRequest));
        loginLog.setLoginName(loginName);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getLoginName(), request.getPassword());
        // AuthenticationManager authenticate进行用户认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            String error = "用户名或密码错误";
            loginLog.setMsg(error);
            // loginLogService.save(loginLog);
            throw new RuntimeException(error);
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        LoginResponse response = new LoginResponse();
        response.setToken(jwt);
        response.setUsername(loginName);
        loginLog.setMsg("登录成功");
        loginLog.setIsSuccess(AuthConst.SUCCESS);
        // TODO 改成异步插入
        // loginLogService.save(loginLog);
        return new ApiResponse<>(response);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public ApiResponse<Object> logout() {
        boolean success = loginService.logout();
        return new ApiResponse<>(success);
    }

}
