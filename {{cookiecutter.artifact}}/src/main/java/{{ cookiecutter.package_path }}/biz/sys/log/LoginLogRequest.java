package {{ cookiecutter.basePackage }}.biz.sys.log;

import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import {{ cookiecutter.basePackage }}.common.request.sort.Sortable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 登录日志分页查询请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginLogRequest extends PaginationRequest {

    /**
     * 登录账号
     */
    private String account;

    /**
     * 登录时间
     */
    @Sortable
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;

    /**
     * 登录是否成功
     */
    private Boolean success;
}
