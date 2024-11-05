package {{ cookiecutter.basePackage }}.biz.sys.monitor;

import lombok.Data;

/**
 * jvm信息响应
 */
@Data
public class JvmResponse {

    /**
     * 进程id
     */
    private Integer processId;

    /**
     * 进程名称
     */
    private String processName;
}
