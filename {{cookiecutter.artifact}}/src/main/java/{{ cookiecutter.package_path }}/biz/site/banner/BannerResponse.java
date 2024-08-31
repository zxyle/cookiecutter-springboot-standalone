package {{ cookiecutter.basePackage }}.biz.site.banner;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Banner响应类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BannerResponse {

    /**
     * 创建时间
     *
     * @mock CURRENT_TIMESTAMP
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    /**
     * 更新时间
     *
     * @mock CURRENT_TIMESTAMP
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

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