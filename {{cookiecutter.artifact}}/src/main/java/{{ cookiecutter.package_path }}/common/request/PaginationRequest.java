package {{ cookiecutter.basePackage }}.common.request;

import javax.validation.constraints.NotNull;

/**
 * 分页请求对象
 */
public class PaginationRequest extends BaseRequest {

    /**
     * 当前页面的编号
     *
     * @mock 1
     */
    private Integer pageNum;

    /**
     * 当前页面的编号(兼容pageNum)
     *
     * @mock 1
     */
    private Integer current;

    /**
     * 页面大小
     * @mock 10
     */
    @NotNull
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
