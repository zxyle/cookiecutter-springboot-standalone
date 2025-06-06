package {{ cookiecutter.basePackage }}.biz.sys.area;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.LiteEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 行政区划
 */
@Data
@TableName("sys_area")
@EqualsAndHashCode(callSuper = false)
public class Area extends LiteEntity {

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
     * 父级行政区编码
     *
     * @mock 330110
     */
    private String parentId;

    /**
     * 行政区级别（1-国家 2-省级 3-市级 4-县区级 5-镇街级 6-村社级）
     *
     * @mock 5
     */
    private Integer level;

}
