package {{ cookiecutter.basePackage }}.biz.auth.login;

import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.user.UserService;
import {{ cookiecutter.basePackage }}.config.security.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 登录服务实现
 */
@Service
@RequiredArgsConstructor
public class LoginService {

    final AuthenticationManager authenticationManager;
    final UserService userService;

    /**
     * 用户登录
     *
     * @param account  用户名/邮箱/手机号
     * @param password 密码
     * @return 登录响应
     */
    public LoginResponse login(String account, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(account, password);
        // 进行用户认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证成功后，将认证信息存入SecurityContextHolder中
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        User user = loginUser.getUser();
        return new LoginResponse(user);
    }

    /**
     * 退出登录
     *
     * @param userId 用户id
     * @return 是否成功
     */
    public boolean logout(Integer userId) {
        // 清除登录信息
        SecurityContextHolder.clearContext();
        // 删除redis中用户权限缓存
        return userService.kick(userId);
    }

    /**
     * 登录后操作
     */
    @Async
    public void afterLogin(LoginResponse response) {
        User user = new User();
        user.setId(response.getUserId());

        // 用户初次登录后，需要在24小时内修改密码，否则到期后无法登录
        if (response.isMustChangePwd()) {
            response.setMustChangePwd(true);
            user.setExpireTime(LocalDateTime.now().plusHours(24));
        }

        // 更新用户最后登录时间
        user.setLastLoginTime(LocalDateTime.now());
        userService.updateById(user);
    }
}
