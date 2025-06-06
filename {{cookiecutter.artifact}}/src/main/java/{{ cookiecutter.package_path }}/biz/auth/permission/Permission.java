package {{ cookiecutter.basePackage }}.biz.auth.permission;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限
 */
@Data
@TableName("auth_permission")
@EqualsAndHashCode(callSuper = false)
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限代码
     */
    private String code;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 父级权限ID
     */
    private Integer parentId;

    /**
     * 权限类型（1：页面/路由，2：接口/功能，3：按钮/组件）
     */
    private Integer kind;

    /**
     * 页面路由（用于前端控制）
     */
    private String path;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 角色ID
     */
    @TableField(exist = false)
    private Integer roleId;

}
