package {{ cookiecutter.basePackage }}.config.security.wechat;

import {{ cookiecutter.basePackage }}.biz.auth.login.WechatLoginRequest;
import {{ cookiecutter.basePackage }}.biz.site.miniprogram.WeChatMiniProgramService;
import {{ cookiecutter.basePackage }}.biz.site.miniprogram.WechatLoginResponse;
import {{ cookiecutter.basePackage }}.biz.site.miniprogram.WxUserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * 微信小程序手机号授权登录过滤器
 */
@Slf4j
public class WechatAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 登录请求体json 账号key
     */
    public static final String ACCOUNT = "account";
    public static final String USER_ID = "userId";

    private final WeChatMiniProgramService wechatMiniProgramService;

    public WechatAuthenticationFilter(WeChatMiniProgramService wechatMiniProgramService) {
        // 处理的验证码登录请求处理url
        super(new AntPathRequestMatcher("/auth/login/wechat", HttpMethod.POST.name()));
        this.wechatMiniProgramService = wechatMiniProgramService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException {
        if (!req.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("不支持的Http方法: {}" + req.getMethod());
        }

        // 从请求body中获取登录参数
        WechatLoginRequest loginRequest = obtainCodeLoginRequest(req);
        String jsCode = loginRequest.getJsCode();
        String encryptedData = loginRequest.getEncryptedData();
        String iv = loginRequest.getIv();

        WechatLoginResponse wechatLoginResponse = wechatMiniProgramService.login(jsCode);
        log.info("微信登录响应: {}", wechatLoginResponse);
        if (wechatLoginResponse == null || StringUtils.isBlank(wechatLoginResponse.getSessionKey())) {
            throw new AuthenticationServiceException("微信登录失败");
        }

        WxUserInfo userInfo = wechatMiniProgramService.getUserInfo(encryptedData, wechatLoginResponse.getSessionKey(), iv);
        log.info("微信用户信息: {}", userInfo);
        String account = userInfo.getPurePhoneNumber();
        if (StringUtils.isBlank(account)) {
            throw new AuthenticationServiceException("微信用户信息获取失败");
        }

        account = account.trim();
        req.setAttribute(ACCOUNT, account);
        // 创建WechatAuthenticationToken(未认证)
        WechatAuthenticationToken authRequest = new WechatAuthenticationToken(account, wechatLoginResponse.getOpenid());

        // 设置用户信息
        setDetails(req, authRequest);
        // 返回Authentication实例
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    protected WechatLoginRequest obtainCodeLoginRequest(HttpServletRequest req) throws JsonProcessingException {
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
        log.info("微信授权登录请求：{}", requestBody);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(requestBody, WechatLoginRequest.class);
    }

    protected void setDetails(HttpServletRequest request, WechatAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
