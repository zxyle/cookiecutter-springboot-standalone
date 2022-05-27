package {{ cookiecutter.basePackage }}.biz.sys.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * IP白名单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_whitelist")
public class Whitelist extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * IP白名单
     */
    @ExcelProperty("IP白名单")
    private String ip;


}
