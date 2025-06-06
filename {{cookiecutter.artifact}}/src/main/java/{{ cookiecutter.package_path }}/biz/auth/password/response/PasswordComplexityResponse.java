package {{ cookiecutter.basePackage }}.biz.auth.password.response;

import lombok.Data;

/**
 * 密码复杂度响应
 */
@Data
public class PasswordComplexityResponse {

    /**
     * 密码复杂分（0-5分）
     *
     * @mock 5
     */
    private Integer score;

    /**
     * 密码复杂度提示
     *
     * @mock 密码复杂度：强
     */
    private String message;

    public PasswordComplexityResponse(Integer score) {
        this.score = score;
    }

    public String getMessage() {
        switch (score) {
            case 0:
            case 1:
                message = "密码复杂度：弱";
                break;
            case 2:
            case 3:
                message = "密码复杂度：中";
                break;
            case 4:
            case 5:
                message = "密码复杂度：强";
                break;
            default:
                message = "密码复杂度：未知";
        }
        return message;
    }
}
