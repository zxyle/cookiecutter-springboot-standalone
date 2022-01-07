package {{ cookiecutter.basePackage }}.biz.sample.entity;

import {{ cookiecutter.basePackage }}.base.model.BaseEntity;
import {{ cookiecutter.basePackage }}.common.enums.SexEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    private String username;
    private String password;
    private Integer age;
    private String email;
    private SexEnum sex;
}