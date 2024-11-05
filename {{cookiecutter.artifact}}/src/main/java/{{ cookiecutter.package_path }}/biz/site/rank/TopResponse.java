package {{ cookiecutter.basePackage }}.biz.site.rank;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 排行榜成员响应
 */
@Data
@AllArgsConstructor
public class TopResponse {

    /**
     * 成员ID
     *
     * @mock 16
     */
    private String member;

    /**
     * 成员名称
     *
     * @mock 王晓
     */
    private String name;

    /**
     * 分数
     *
     * @mock 100
     */
    private Double score;
}