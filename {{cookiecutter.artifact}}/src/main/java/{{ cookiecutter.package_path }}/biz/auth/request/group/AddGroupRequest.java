package {{ cookiecutter.basePackage }}.biz.auth.request.group;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class AddGroupRequest {

    /**
     * 用户组名称
     *
     * @mock 技术部
     */
    @NotBlank
    private String name;

    /**
     * 上级用户组ID
     *
     * @mock 1
     */
    @NotNull
    private Long parentId;

    /**
     * 排序号
     *
     * @mock 1
     */
    @Positive
    private Integer sortNo;

    /**
     * 描述信息
     */
    private String description;

    // 负责人
    // 联系电话
    // 邮箱
    // 用户组状态

}
