package {{ cookiecutter.basePackage }}.biz.auth.login;

import {{ cookiecutter.basePackage }}.biz.auth.profile.Profile;
import {{ cookiecutter.basePackage }}.biz.auth.user.User;
import {{ cookiecutter.basePackage }}.biz.auth.token.JwtUtil;
import lombok.Data;
import {{ cookiecutter.basePackage }}.common.constant.AuthConst;

/**
 * 用户登录响应
 */
@Data
public class LoginResponse {

    /**
     * 用户ID
     *
     * @mock 10
     */
    private Integer userId;

    /**
     * 用户资料
     */
    private Profile profile;

    /**
     * 昵称/名字/真实姓名（只用于展示）
     *
     * @mock 用户_ISDZsk
     */
    private String nickname;

    /**
     * JWT令牌，放在Authorization请求头中
     *
     * @mock Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMCIsImlhdCI6MTU4MzY5NjQwMCwiZXhwIj
     */
    private String token;

    /**
     * 是否管理员
     *
     * @mock false
     */
    private boolean admin;

    /**
     * 是否需要修改密码, 返回true后, 需跳转到修改密码页面
     *
     * @mock false
     */
    private boolean mustChangePwd;

    // TODO 考虑增加一个头像Url

    public LoginResponse(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.admin = user.getAdmin();
        this.mustChangePwd = user.getMustChangePwd();
        this.token = AuthConst.AUTH_TYPE + JwtUtil.createJwt(userId.toString());
    }
}
