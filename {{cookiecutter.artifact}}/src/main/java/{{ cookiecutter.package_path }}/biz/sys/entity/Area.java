package {{ cookiecutter.basePackage }}.biz.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 行政区
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_area")
public class Area extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 行政区编码
     *
     * @mock 330110005
     */
    private String code;

    /**
     * 行政区名称
     *
     * @mock 五常街道
     */
    private String name;

    /**
     * 上级编码
     *
     * @mock 330110
     */
    private String parent;

    /**
     * 行政区级别 （1-国家 2-省级 3-市级 4-县区级 5-镇街级 6-社区、村级）
     *
     * @mock 5
     */
    private Integer level;

}
