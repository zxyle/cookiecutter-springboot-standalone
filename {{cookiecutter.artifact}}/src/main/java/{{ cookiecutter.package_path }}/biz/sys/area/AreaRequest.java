package {{ cookiecutter.basePackage }}.biz.sys.area;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;

/**
 * 行政区查询请求
 */
@Data
public class AreaRequest {

    private static final int DEFAULT_LEVEL = 4;
    private static final String DEFAULT_ROOT_ID = "0000";

    /**
     * 根节点ID
     *
     * @mock 3301
     */
    private String rootId;

    /**
     * 查询最小级别（1-国家 2-省级 3-市级 4-区县 5-镇街 6-村社）
     *
     * @mock 4
     */
    @Range(min = 1, max = 6, message = "查询最小级别必须在1-6之间")
    private Integer level;

    public String getRootId() {
        return StringUtils.isBlank(rootId) ? DEFAULT_ROOT_ID : rootId;
    }

    public Integer getLevel() {
        return level == null ? DEFAULT_LEVEL : level;
    }
}
