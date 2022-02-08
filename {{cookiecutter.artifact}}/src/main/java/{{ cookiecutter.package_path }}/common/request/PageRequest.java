package {{ cookiecutter.basePackage }}.common.request;

/**
 * pagination request object
 */
public class PageRequest extends BaseRequest {

    /**
     * number of the current page
     */
    private Integer pageNum;

    /**
     * size of the pages
     */
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
