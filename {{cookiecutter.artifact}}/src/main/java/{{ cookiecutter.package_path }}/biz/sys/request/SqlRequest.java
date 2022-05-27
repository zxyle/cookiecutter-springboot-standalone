package {{ cookiecutter.basePackage }}.biz.sys.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SqlRequest {

    @NotBlank
    private String sql;

}
