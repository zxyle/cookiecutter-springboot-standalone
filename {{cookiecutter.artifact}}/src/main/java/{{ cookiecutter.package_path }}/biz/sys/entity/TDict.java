package {{ cookiecutter.basePackage }}.biz.sys.entity;

import {{ cookiecutter.basePackage }}.base.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 字典数据表
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TDict extends BaseEntity {

    private static final long serialVersionUID = -3437186795615187912L;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典排序
     */
    private Integer dictSort;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 样式属性（其他样式扩展）
     */
    private String cssClass;

    /**
     * 表格回显样式
     */
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    private String isDefault;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 修改人
     */
    private String modifyBy;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 备注
     */
    private String remark;


}
