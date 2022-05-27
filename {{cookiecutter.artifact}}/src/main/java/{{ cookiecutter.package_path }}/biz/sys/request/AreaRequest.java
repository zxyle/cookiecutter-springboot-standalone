package {{ cookiecutter.basePackage }}.biz.sys.request;

import lombok.Data;

@Data
public class AreaRequest {

    /**
     * 根节点ID
     */
    private String rootId;

    /**
     * 最小级别
     */
    private Integer level;
}
