package {{ cookiecutter.basePackage }}.biz.auth.group;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;
import java.util.Set;

/**
 * 创建用户组请求
 */
@Data
public class AddGroupRequest {

    /**
     * 用户组名称
     *
     * @mock 技术部
     */
    @Length(max = 16, message = "用户组名称长度不能超过16个字符")
    @NotBlank(message = "用户组名称不能为空")
    private String name;

    /**
     * 父级用户组ID
     *
     * @mock 1
     */
    @Positive(message = "用户组ID必须为正整数")
    @NotNull(message = "父级用户组ID不能为空")
    private Integer parentId;

    /**
     * 排序号
     *
     * @mock 1
     */
    @Positive(message = "排序号必须为正整数")
    private Integer sort;

    /**
     * 描述信息
     *
     * @mock 测试组
     */
    @Length(max = 32, message = "描述信息长度不能超过32个字符")
    private String description;

    /**
     * 权限id集合
     */
    private Set<@Positive @NotNull Integer> permissionIds;

    /**
     * 角色id集合
     */
    private Set<@Positive @NotNull Integer> roleIds;

}
