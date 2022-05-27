package {{ cookiecutter.basePackage }}.biz.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模板表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_blacklist")
public class Blacklist extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * IP地址
     */
    private String ip;

}
