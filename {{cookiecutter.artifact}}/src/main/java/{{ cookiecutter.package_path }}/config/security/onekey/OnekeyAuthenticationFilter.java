package {{ cookiecutter.basePackage }}.config.security.onekey;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import {{ cookiecutter.basePackage }}.biz.auth.login.OneKeyLoginRequest;
import {{ cookiecutter.basePackage }}.biz.auth.login.OneKeyLoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import {{ cookiecutter.namespace }}.servlet.http.HttpServletRequest;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 本机手机号授权登录过滤器
 */
@Slf4j
public class OnekeyAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 登录请求体json 账号key
     */
    public static final String ACCOUNT = "account";
    public static final String USER_ID = "userId";

    private final OneKeyLoginService oneKeyLoginService;

    public OnekeyAuthenticationFilter(OneKeyLoginService oneKeyLoginService) {
        // 处理的验证码登录请求处理url
        super(new AntPathRequestMatcher("/auth/login/onekey", HttpMethod.POST.name()));
        this.oneKeyLoginService = oneKeyLoginService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException {
        if (!req.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("不支持的Http方法: {}" + req.getMethod());
        }

        // 从请求body中获取登录参数
        OneKeyLoginRequest loginRequest = obtainCodeLoginRequest(req);
        String accessToken = loginRequest.getToken();
        String account = oneKeyLoginService.getPhone(accessToken);
        if (StringUtils.isBlank(account)) {
            throw new AuthenticationServiceException("微信用户信息获取失败");
        }

        account = account.trim();
        req.setAttribute(ACCOUNT, account);
        // 创建WechatAuthenticationToken(未认证)
        OnekeyAuthenticationToken authRequest = new OnekeyAuthenticationToken(account, "");

        // 设置用户信息
        setDetails(req, authRequest);
        // 返回Authentication实例
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    protected OneKeyLoginRequest obtainCodeLoginRequest(HttpServletRequest req) throws JsonProcessingException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = req.getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }

        } catch (IOException e) {
            log.error("读取请求body失败", e);
        }

        String requestBody = stringBuilder.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(requestBody, OneKeyLoginRequest.class);
    }

    protected void setDetails(HttpServletRequest request, OnekeyAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
