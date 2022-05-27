package {{ cookiecutter.basePackage }}.common.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class AntdProTable<T> {
    /**
     * 当前页
     */
    private Long current;

    /**
     * 数据列表
     */
    private List<T> data;

    /**
     * 页面大小
     */
    private Long pageSize;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 总数
     */
    private Long total;

}
