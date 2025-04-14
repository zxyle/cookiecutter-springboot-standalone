package {{ cookiecutter.basePackage }}.config.security.onekey;

import {{ cookiecutter.basePackage }}.biz.sys.log.LoginLog;
import {{ cookiecutter.basePackage }}.biz.sys.log.LoginLogService;
import {{ cookiecutter.basePackage }}.common.enums.ErrorEnum;
import {{ cookiecutter.basePackage }}.common.response.R;
import {{ cookiecutter.basePackage }}.common.util.IpUtil;
import {{ cookiecutter.basePackage }}.common.util.JacksonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 本机手机号授权登录失败处理
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OnekeyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    final LoginLogService loginLogService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 登录失败后，记录登录日志
        recordLog(request, exception);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(Objects.requireNonNull(JacksonUtil.serialize(R.fail(ErrorEnum.AUTH_ERROR))));
    }

    private void recordLog(HttpServletRequest request, AuthenticationException exception) {
        String account = (String) request.getAttribute(OnekeyAuthenticationFilter.ACCOUNT);
        LoginLog loginLog = new LoginLog();
        loginLog.setIp(IpUtil.getIpAddr(request));
        loginLog.setUa(request.getHeader(HttpHeaders.USER_AGENT));
        loginLog.setSuccess(false);
        loginLog.setAccount(account);
        loginLog.setMsg(exception.getMessage());
        loginLogService.saveLogAsync(loginLog);
    }
}
