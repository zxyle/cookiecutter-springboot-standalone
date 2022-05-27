package {{ cookiecutter.basePackage }}.biz.user.request;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    /**
     * 真实姓名
     *
     * @mock 张三
     */
    private String realName;

    /**
     * 手机号
     *
     * @mock 13111111111
     */
    private String mobile;

    /**
     * 邮箱
     *
     * @mock me@example.com
     */
    private String email;

    /**
     * 电话号
     *
     * @mock 123456
     */
    private String telephone;
}
