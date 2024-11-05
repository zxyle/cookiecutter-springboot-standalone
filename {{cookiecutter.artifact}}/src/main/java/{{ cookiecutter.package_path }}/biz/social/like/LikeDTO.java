package {{ cookiecutter.basePackage }}.biz.social.like;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 点赞列表信息
 */
@Data
public class LikeDTO {

    /**
     * 资源id
     *
     * @mock 1
     */
    private Integer resId;

    /**
     * 点赞时间
     *
     * @mock 2024-05-27 09:54:34
     */
    private LocalDateTime likeTime;

    /**
     * 资源信息
     */
    private Object res;
}
