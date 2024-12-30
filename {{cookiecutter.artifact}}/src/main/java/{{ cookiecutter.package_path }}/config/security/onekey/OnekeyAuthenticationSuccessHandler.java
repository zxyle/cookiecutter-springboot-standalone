package {{ cookiecutter.basePackage }}.config.security.onekey;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import {{ cookiecutter.basePackage }}.biz.auth.login.LoginResponse;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.biz.sys.log.LoginLog;
import {{ cookiecutter.basePackage }}.biz.sys.log.LoginLogService;
import {{ cookiecutter.basePackage }}.common.response.R;
import {{ cookiecutter.basePackage }}.common.util.IpUtil;
import {{ cookiecutter.basePackage }}.config.security.LoginUser;
import io.github.zxyle.map.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Map;


/**
 * 本机手机号授权登录成功处理
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OnekeyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    final LoginLogService loginLogService;
    static StringRedisTemplate stringRedisTemplate;
    final UserService usersService;
    final MapService mapService;

    // 解决 @Component 下 @Autowired 注入为null的情况
    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        OnekeyAuthenticationSuccessHandler.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        request.setAttribute(OnekeyAuthenticationFilter.USER_ID, principal.getUser().getId());

        // 登录成功后，记录登录日志
        recordLog(request);

        // 登录成功后，返回token
        LoginResponse loginResponse = new LoginResponse(principal.getUser());
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), R.ok(loginResponse, "登录成功"));
    }

    private void recordLog(HttpServletRequest request) {
        String account = (String) request.getAttribute(OnekeyAuthenticationFilter.ACCOUNT);
        Integer userId = (Integer) request.getAttribute(OnekeyAuthenticationFilter.USER_ID);
        LoginLog loginLog = new LoginLog();
        String ip = IpUtil.getIpAddr(request);
        loginLog.setIp(ip);
        loginLog.setUa(request.getHeader(HttpHeaders.USER_AGENT));
        loginLog.setAccount(account);
        loginLog.setMsg("登录成功");
        loginLog.setSuccess(true);
        loginLog.setUserId(userId);
        loginLogService.saveLoginLog(loginLog);

        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId, userId);
        wrapper.set(User::getLastLoginTime, LocalDateTime.now());
        usersService.update(wrapper);
    }
}
