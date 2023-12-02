package {{ cookiecutter.basePackage }}.biz.sys.acl;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;

/**
 * IP访问控制
 */
@Data
@TableName("sys_acl")
@EqualsAndHashCode(callSuper = false)
public class Acl extends BaseEntity {

    /**
     * IP
     */
    private String ip;

    /**
     * 是否允许访问
     */
    private Boolean allowed;

    /**
     * 截止时间
     */
    private LocalDateTime endTime;

    /**
     * 描述信息
     */
    private String description;

}