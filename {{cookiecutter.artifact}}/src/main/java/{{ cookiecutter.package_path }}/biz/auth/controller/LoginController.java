package {{ cookiecutter.basePackage }}.biz.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import {{ cookiecutter.basePackage }}.biz.auth.entity.User;
import {{ cookiecutter.basePackage }}.biz.auth.mapper.UserMapper;
import {{ cookiecutter.basePackage }}.biz.sys.entity.LoginLog;
import {{ cookiecutter.basePackage }}.biz.sys.service.ILoginLogService;
import {{ cookiecutter.basePackage }}.biz.user.request.LoginByNameRequest;
import {{ cookiecutter.basePackage }}.common.response.AntdProResponse;
import {{ cookiecutter.basePackage }}.common.util.IpUtil;
import dev.zhengxiang.tool.crypto.PlainKeyPair;
import dev.zhengxiang.tool.crypto.RSAEncrypt;
import dev.zhengxiang.tool.crypto.Werkzeug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * 登录管理
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ILoginLogService loginLogService;

    Werkzeug werkzeug = new Werkzeug();

    /**
     * 用户登陆
     *
     * @param request 用户信息
     * @apiNote 通过用户名和密码进行用户登录
     */
    @PostMapping("/login")
    public AntdProResponse login(@Valid @RequestBody LoginByNameRequest request, HttpServletRequest servletRequest) {
        String loginName = request.getLoginName();

        // 登录日志
        LoginLog loginLog = new LoginLog();
        loginLog.setUa(servletRequest.getHeader(HttpHeaders.USER_AGENT));
        loginLog.setIp(IpUtil.getIpAddr(servletRequest));
        loginLog.setLoginName(loginName);

        User user = userMapper.queryByLoginName(loginName);

        AntdProResponse response = new AntdProResponse();

        // 登录成功
        if (null != user && werkzeug.checkPasswordHash(user.getPwd(), request.getPassword())) {
            loginLog.setMsg("登录成功");
            StpUtil.login(user.getId());
            response.setStatus("ok");
            response.setSuccess(true);
            response.setCurrentAuthority("admin");
            response.setType("account");
            response.setName(user.getRealName());
            loginLogService.save(loginLog);
            return response;
        }

        loginLog.setMsg("登录失败");
        loginLogService.save(loginLog);
        return response;
    }

    /**
     * 退出登陆
     */
    @PostMapping("/logout")
    public AntdProResponse logout() {
        // 会话失效
        StpUtil.logout();
        AntdProResponse response = new AntdProResponse();
        response.setSuccess(true);
        return response;
    }

    /**
     * 查询登录状态
     */
    @RequestMapping("/isLogin")
    public AntdProResponse isLogin() {
        AntdProResponse response = new AntdProResponse();
        response.setSuccess(StpUtil.isLogin());
        return response;
    }

    /**
     * 查询 Token 信息
     */
    @RequestMapping("/tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
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
