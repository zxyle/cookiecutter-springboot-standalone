package {{ cookiecutter.basePackage }}.biz.sys.log;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.LiteEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 操作日志
 */
@Data
@TableName("sys_operate_log")
@EqualsAndHashCode(callSuper = false)
public class OperateLog extends LiteEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operateTime;

    /**
     * 操作用户ID
     */
    private Integer userId;

    /**
     * 业务模块
     */
    private String biz;

    /**
     * 操作名称
     */
    private String operationName;

    /**
     * 操作对象ID
     */
    private String request;

    /**
     * 请求来源IP
     */
    private String ip;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 操作是否成功
     */
    private Boolean success;

    /**
     * 链路追踪ID
     */
    private String traceId;

    /**
     * 操作结果
     */
    private String response;

    /**
     * 操作耗时
     */
    private Long measured;

}
