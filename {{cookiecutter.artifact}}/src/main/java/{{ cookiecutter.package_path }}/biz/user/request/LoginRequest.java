package {{ cookiecutter.basePackage }}.biz.user.request;


import javax.validation.constraints.NotNull;

public class LoginRequest {

    /**
     * 用户名
     *
     * @mock admin
     * @since v1.0
     */
    @NotNull
    private String username;

    /**
     * 密码
     *
     * @mock 12345678
     * @since v1.0
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
