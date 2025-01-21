package {{ cookiecutter.basePackage }}.biz.site.banner;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 轮播图
 */
@Data
@TableName("site_banner")
@EqualsAndHashCode(callSuper = false)
public class Banner extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     *
     * @mock 
     */
    private String name;

    /**
     * 标题
     *
     * @mock 
     */
    private String title;

    /**
     * 描述
     *
     * @mock 
     */
    private String description;

    /**
     * 图片路径或URL
     *
     * @mock 
     */
    private String imageUrl;

    /**
     * 链接
     *
     * @mock 
     */
    private String linkUrl;

    /**
     * 展示顺序，越小越靠前
     *
     * @mock 0
     */
    private Integer sortOrder;

    /**
     * 状态 (1:启用, 0:禁用)
     *
     * @mock 0
     */
    private Integer status;

    /**
     * 展示位置
     *
     * @mock 
     */
    private String position;

}