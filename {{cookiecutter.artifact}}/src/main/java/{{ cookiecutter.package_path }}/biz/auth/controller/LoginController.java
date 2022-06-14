package {{ cookiecutter.basePackage }}.biz.auth.controller;

import {{ cookiecutter.basePackage }}.biz.auth.constant.AuthConstant;
import {{ cookiecutter.basePackage }}.biz.auth.security.LoginUser;
import {{ cookiecutter.basePackage }}.biz.auth.service.LoginService;
import {{ cookiecutter.basePackage }}.biz.sys.entity.LoginLog;
import {{ cookiecutter.basePackage }}.biz.sys.service.ILoginLogService;
import {{ cookiecutter.basePackage }}.biz.auth.request.login.LoginByNameRequest;
import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.util.IpUtil;
import {{ cookiecutter.basePackage }}.biz.auth.util.JwtUtil;
import dev.zhengxiang.tool.crypto.PlainKeyPair;
import dev.zhengxiang.tool.crypto.RSAEncrypt;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 登录管理
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ILoginLogService loginLogService;

    @Autowired
    LoginService loginService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 用户登录
     *
     * @param request 用户信息
     * @apiNote 通过用户名和密码进行用户登录
     */
    @PostMapping("/login")
    public ApiResponse<Map<String, String>> login(@Valid @RequestBody LoginByNameRequest request, HttpServletRequest servletRequest) {
        String loginName = request.getLoginName();

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
            loginLogService.save(loginLog);
            throw new RuntimeException(error);
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        map.put("loginName", loginName);
        map.put("realName", loginUser.getUser().getRealName());
        // TODO 头像URL
        loginLog.setMsg("登录成功");
        loginLog.setIsSuccess(AuthConstant.SUCCESS);
        loginLogService.save(loginLog);
        return new ApiResponse<>(map);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public ApiResponse<Object> logout() {
        boolean success = loginService.logout();
        return new ApiResponse<>("退出登录成功", success);
    }

    /**
     * 获取RSA公钥
     */
    @RequestMapping("/getPublicKey")
    public String getPublicKey() {
        String sessionId = "";
        String key = "privateKey:".concat(sessionId);

        try {
            // 生成一对公钥和私钥
            PlainKeyPair plainKeyPair = RSAEncrypt.genKeyPair();
            String privateKey = plainKeyPair.getPrivateKey();
            stringRedisTemplate.opsForValue().set(key, privateKey, 60, TimeUnit.SECONDS);
            // 将公钥传递给前端
            return plainKeyPair.getPublicKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 前端使用公钥将关键信息加密，传递给后端

        // 后端使用私钥进行解密
        return "";

    }

}
