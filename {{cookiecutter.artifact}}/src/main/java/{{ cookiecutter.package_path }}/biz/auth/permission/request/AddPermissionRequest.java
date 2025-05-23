package {{ cookiecutter.basePackage }}.biz.auth.permission.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;
import {{ cookiecutter.namespace }}.validation.constraints.NotNull;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;

/**
 * 新增权限请求
 */
@Data
public class AddPermissionRequest {

    /**
     * 权限名称
     *
     * @mock 新增新闻
     */
    @Length(max = 255, message = "权限名称长度不能超过255个字符")
    @NotBlank(message = "权限名称不能为空")
    private String name;

    /**
     * 权限代码
     *
     * @mock main:news:add
     */
    @Length(max = 255, message = "权限代码长度不能超过255个字符")
    @NotBlank(message = "权限代码不能为空")
    private String code;

    /**
     * 描述信息
     *
     * @mock 新增新闻权限
     */
    @Length(max = 255, message = "描述信息长度不能超过255个字符")
    private String description;

    /**
     * 父级权限ID
     *
     * @mock 1
     */
    @Positive(message = "父级权限ID必须为正整数")
    private Integer parentId;

    /**
     * 权限类型（1：页面/路由，2：接口/功能，3：按钮/组件）  TODO 权限类型: ROUTE=页面/路由, API=接口/功能, COMPONENT=按钮/组件
     *
     * @mock 2
     */
    @Range(min = 1, max = 3, message = "权限类型为1~3之间")
    @NotNull(message = "权限类型不能为空")
    private Integer kind;

    /**
     * 页面路由（用于前端控制）
     *
     * @mock /news/add
     */
    @Length(max = 255, message = "页面路由长度不能超过255个字符")
    private String path;

    /**
     * 显示顺序
     *
     * @mock 1
     */
    @Positive(message = "显示顺序必须为正数")
    private Integer sort;
}
