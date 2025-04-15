package {{ cookiecutter.basePackage }}.config.security.mobile;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import {{ cookiecutter.basePackage }}.biz.auth.login.LoginResponse;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.common.util.IpUtil;
import {{ cookiecutter.basePackage }}.config.security.LoginUser;
import {{ cookiecutter.basePackage }}.biz.sys.log.LoginLog;
import {{ cookiecutter.basePackage }}.biz.sys.log.LoginLogService;
import {{ cookiecutter.basePackage }}.common.response.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


/**
 * 验证码登录成功处理
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SmsCodeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    final LoginLogService loginLogService;
    static StringRedisTemplate stringRedisTemplate;
    final UserService usersService;

    // 解决 @Component 下 @Autowired 注入为null的情况
    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        SmsCodeAuthenticationSuccessHandler.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        request.setAttribute(SmsCodeAuthenticationFilter.USER_ID, principal.getUser().getId());

        // 登录成功后，记录登录日志
        recordLog(request);

        // 登录成功后，返回token
        LoginResponse loginResponse = new LoginResponse(principal.getUser());
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), R.ok(loginResponse, "登录成功"));
    }

    private void recordLog(HttpServletRequest request) {
        String account = (String) request.getAttribute(SmsCodeAuthenticationFilter.ACCOUNT);
        Integer userId = (Integer) request.getAttribute(SmsCodeAuthenticationFilter.USER_ID);
        LoginLog loginLog = new LoginLog();
        loginLog.setIp(IpUtil.getIpAddr(request));
        loginLog.setUa(request.getHeader(HttpHeaders.USER_AGENT));
        loginLog.setAccount(account);
        loginLog.setMsg("登录成功");
        loginLog.setSuccess(true);
        loginLog.setUserId(userId);
        loginLogService.saveLogAsync(loginLog);

        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId, userId);
        wrapper.set(User::getLastLoginTime, LocalDateTime.now());
        wrapper.set(User::getLastIp, IpUtil.getIpAddr(request));
        usersService.update(wrapper);
    }
}
