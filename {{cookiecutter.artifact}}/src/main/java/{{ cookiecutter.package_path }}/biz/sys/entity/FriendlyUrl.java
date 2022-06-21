package {{ cookiecutter.basePackage }}.biz.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 友链
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_friendly_url")
public class FriendlyUrl extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 链接文本
     */
    private String content;

    /**
     * 链接
     */
    private String url;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 1-启用 0-禁用
     */
    private Integer status;

}
