package {{ cookiecutter.basePackage }}.biz.sys.request;

import {{ cookiecutter.basePackage }}.common.request.PaginationRequest;
import lombok.Data;

@Data
public class OperateLogRequest extends PaginationRequest {

    private String startDate;

    private String endDate;
}
