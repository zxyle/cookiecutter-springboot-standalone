package {{ cookiecutter.basePackage }}.biz.social.star;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 收藏对象
 */
@Data
public class StarDTO {

    /**
     * 资源id
     *
     * @mock 1
     */
    private Integer resId;

    /**
     * 收藏时间
     *
     * @mock 2024-05-27 09:54:34
     */
    private LocalDateTime starTime;
}
