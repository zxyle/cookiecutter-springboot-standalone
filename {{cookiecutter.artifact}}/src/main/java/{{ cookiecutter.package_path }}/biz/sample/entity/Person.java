package {{ cookiecutter.basePackage }}.biz.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    // 数据库主键id为正数, 加上注解 可以防止缓存穿透, 负数为@Negtive
    // @Positive
    // private Integer id;

    @NotNull(message = "classId 不能为空")
    @NotBlank(message = "不允许空字符")
    private String classId;

    @Size(max = 33)
    @NotNull(message = "name 不能为空")
    @NotBlank
    private String name;

    @Pattern(regexp = "(^Man$|^Woman$|^UGM$)", message = "sex 值不在可选范围")
    @NotNull(message = "sex 不能为空")
    private String sex;

    @Email(message = "email 格式不正确")
    @NotNull(message = "email 不能为空")
    @NotBlank
    private String email;

    private String id;

}

