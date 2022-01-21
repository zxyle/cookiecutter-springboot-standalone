package {{ cookiecutter.basePackage }}.biz.user.response;


import lombok.Data;

import java.util.Date;

@Data
public class LoginResponse {
    /**
     * 用户名
     *
     * @mock admin
     */
    private String username;

    /**
     * 年龄
     *
     * @mock 18
     */
    private Integer age;

    /**
     * 性别
     *
     * @mock 男
     */
    private String gender;

    /**
     * 生日
     *
     * @mock 1979-10-27
     */
    private Date birthday;

}
