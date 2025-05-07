package {{ cookiecutter.basePackage }}.biz.auth.permission;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SecurityPermissions {

    private List<String> permissions;

    private List<Integer> roleIds;
}
