package {{ cookiecutter.basePackage }}.common.response;

import lombok.Data;

import java.util.List;

/**
 * 分页响应对象
 */
@Data
public class PageResponse<T> {
    /**
     * 当前页
     */
    private Integer current;

    /**
     * 页数量
     */
    private Integer pageSize;

    /**
     * 总数
     */
    private Long total;

    /**
     * 数据列表
     */
    private List<T> data;

}