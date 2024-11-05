package {{ cookiecutter.basePackage }}.biz.sys.log;

import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.request.sort.Sortable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import {{ cookiecutter.namespace }}.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * 操作日志分页查询请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OperateLogRequest extends PaginationRequest {

    /**
     * 操作用户ID
     */
    @Positive(message = "用户ID必须是正数")
    private Integer userId;

    /**
     * 业务模块
     */
    private String biz;

    /**
     * 操作时间
     */
    @Sortable
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operateTime;

    /**
     * 操作是否成功
     */
    private Boolean success;

    /**
     * 链路追踪ID
     *
     * @mock f404bb73b28e4757ab20f7305c2426c0
     */
    private String traceId;
}
