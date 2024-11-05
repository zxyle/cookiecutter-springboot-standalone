package {{ cookiecutter.basePackage }}.biz.auth.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {

    private String token;
}
